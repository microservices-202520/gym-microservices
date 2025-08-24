package co.analisys.gimnasio.clase.service;

import co.analisys.gimnasio.clase.client.EntrenadorClient;
import co.analisys.gimnasio.clase.dto.ClaseConEntrenadorDto;
import co.analisys.gimnasio.clase.dto.EntrenadorDto;
import co.analisys.gimnasio.clase.model.Clase;
import co.analisys.gimnasio.clase.repository.ClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClaseService {
    @Autowired
    private ClaseRepository claseRepository;
    
    @Autowired
    private EntrenadorClient entrenadorClient;

    public Clase programarClase(Clase clase) {
        return claseRepository.save(clase);
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
        return claseRepository.save(clase);
    }

    public void eliminarClase(Long id) {
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
}
