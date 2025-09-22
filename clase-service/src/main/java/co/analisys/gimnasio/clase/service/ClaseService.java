package co.analisys.gimnasio.clase.service;

import co.analisys.gimnasio.clase.client.EntrenadorClient;
import co.analisys.gimnasio.clase.dto.*;
import co.analisys.gimnasio.clase.model.Clase;
import co.analisys.gimnasio.clase.repository.ClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClaseService {
    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private EntrenadorClient entrenadorClient;

    @Autowired
    private KafkaTemplate<String, CambioHorarioDTO> kafkaTemplateCambio;

    private static final String TOPIC = "cambios-horarios-clases";

    @Autowired
    private KafkaTemplate<String, OcupacionClase> kafkaTemplateOcupacion;
    private static final String TOPIC_OCUPACION = "ocupacion-clases";

    @Autowired
    private KafkaTemplate<String, DatosEntrenamiento> kafkaTemplateEntrenamiento;

    private static final String TOPIC_ENTRENAMIENTO = "datos-entrenamiento";

    public Clase programarClase(Clase clase) {
        Clase saved = claseRepository.save(clase);
        publicarCambioHorario(saved, "CREADA");
        return saved;
    }

    public List<Clase> obtenerTodasClases() {
        return claseRepository.findAll();
    }

    public List<ClaseConEntrenadorDto> obtenerClasesConEntrenador() {
        List<Clase> clases = claseRepository.findAll();
        return clases.stream().map(this::convertirAClaseConEntrenadorDto)
                .collect(Collectors.toList());
    }

    public Optional<Clase> obtenerClasePorId(Long id) {
        return claseRepository.findById(id);
    }

    public Optional<ClaseConEntrenadorDto> obtenerClaseConEntrenadorPorId(Long id) {
        Optional<Clase> clase = claseRepository.findById(id);
        return clase.map(this::convertirAClaseConEntrenadorDto);
    }

    public Clase actualizarClase(Long id, Clase clase) {
        clase.setId(id);
        Clase saved = claseRepository.save(clase);
        publicarCambioHorario(saved, "ACTUALIZADA");
        return saved;
    }

    public void eliminarClase(Long id) {
        Optional<Clase> clase = claseRepository.findById(id);
        if (clase.isPresent()) {
            publicarCambioHorario(clase.get(), "ELIMINADA");
        }
        claseRepository.deleteById(id);
    }

    public List<Clase> obtenerClasesPorEntrenador(Long entrenadorId) {
        return claseRepository.findByEntrenadorId(entrenadorId);
    }

    private ClaseConEntrenadorDto convertirAClaseConEntrenadorDto(Clase clase) {
        ClaseConEntrenadorDto dto = new ClaseConEntrenadorDto();
        dto.setId(clase.getId());
        dto.setNombre(clase.getNombre());
        dto.setHorario(clase.getHorario());
        dto.setCapacidadMaxima(clase.getCapacidadMaxima());

        if (clase.getEntrenadorId() != null) {
            EntrenadorDto entrenador = entrenadorClient.obtenerEntrenadorPorId(clase.getEntrenadorId());
            dto.setEntrenador(entrenador);
        }

        return dto;
    }

    private void publicarCambioHorario(Clase clase, String accion) {
        try {
            CambioHorarioDTO dto = new CambioHorarioDTO();
            dto.setClaseId(clase.getId());
            dto.setNombre(clase.getNombre());
            dto.setNuevoHorario(String.valueOf(clase.getHorario()));
            dto.setAccion(accion);
            kafkaTemplateCambio.send(TOPIC, dto);
            System.out.println("Info enviada " + dto);
        } catch (Exception ignored) {}
    }

    public void reportarOcupacion(Long claseId, int valor) {
        OcupacionClase ocupacion = new OcupacionClase(claseId, valor);
        kafkaTemplateOcupacion.send(TOPIC_OCUPACION, String.valueOf(claseId), ocupacion);
        System.out.println("Evento de ocupación enviado " + ocupacion);
    }

    public void registrarEntrenamiento(DatosEntrenamiento dto) {
        kafkaTemplateEntrenamiento.send(TOPIC_ENTRENAMIENTO, String.valueOf(dto.getMiembroId()), dto);
        System.out.println("Evento de entrenamiento enviado: " + dto.getTipoEjercicio() +
                " (" + dto.getDuracionMin() + " min)");
    }
}