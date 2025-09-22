package co.analisys.gimnasio.miembro.service;

import co.analisys.gimnasio.miembro.dto.OcupacionClase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OcupacionClaseConsumer {
    @KafkaListener(
            topics = "ocupacion-clases",
            groupId = "monitoreo-grupo",
            containerFactory = "kafkaListenerContainerFactoryOcupacion"
    )
    public void manejarOcupacion(OcupacionClase ocupacion) {
        System.out.println("Ocupación recibida - Clase: " + ocupacion.getClaseId() +
                " → " + ocupacion.getOcupacion());
    }

}

