package co.analisys.gimnasio.miembro.service;

import co.analisys.gimnasio.miembro.dto.CambioHorarioDTO; // Asegúrate de que este DTO esté disponible (copialo de clase-service si necesario)
import co.analisys.gimnasio.miembro.model.Miembro;
import co.analisys.gimnasio.miembro.repository.MiembroRepository;
import co.analisys.gimnasio.miembro.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MiembroService {
    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private KafkaTemplate<String, CambioHorarioDTO> kafkaTemplate;

    @Value("${app.messaging.notifications.exchange:notificaciones.exchange}")
    private String notificationsExchange;

    @Value("${app.messaging.notifications.routingKey:notificaciones.nueva-inscripcion}")
    private String notificationsRoutingKey;

    public Miembro registrarMiembro(Miembro miembro) {
        Miembro saved = miembroRepository.save(miembro);
        try {
            rabbitTemplate.convertAndSend(notificationsExchange, notificationsRoutingKey, "Nueva inscripcion: " + saved.getId());
            String pagoJson = "{\"miembroId\":" + saved.getId() + ",\"concepto\":\"INSCRIPCION\",\"monto\":100}";
            // Publica a la cola de pagos usando el exchange por defecto (routingKey = nombre de la cola)
            rabbitTemplate.convertAndSend("", RabbitMQConfig.PAGOS_QUEUE, pagoJson);
        } catch (Exception ignored) {}
        return saved;
    }

    public List<Miembro> obtenerTodosMiembros() {
        return miembroRepository.findAll();
    }

    public Optional<Miembro> obtenerMiembroPorId(Long id) {
        return miembroRepository.findById(id);
    }

    public Miembro actualizarMiembro(Long id, Miembro miembro) {
        miembro.setId(id);
        return miembroRepository.save(miembro);
    }

    public void eliminarMiembro(Long id) {
        miembroRepository.deleteById(id);
    }

    @KafkaListener(topics = "cambios-horarios-clases", groupId = "gimnasio-miembros-group")
    public void manejarCambioHorario(CambioHorarioDTO cambio) {
        try {
            System.out.println("Cambio de horario detectado - Clase: " + cambio.getNombre() +
                    ", ID: " + cambio.getClaseId() +
                    ", Nuevo Horario: " + cambio.getNuevoHorario() +
                    ", Acción: " + cambio.getAccion());
        } catch (Exception e) {
            System.err.println("Error procesando cambio de horario: " + e.getMessage());
        }
    }
}