package co.analisys.gimnasio.clase.service;

import co.analisys.gimnasio.clase.dto.OcupacionClase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OcupacionClaseProducer {
    @Autowired
    private KafkaTemplate<String, OcupacionClase> kafkaTemplateOcupacion;

    private static final String TOPIC = "ocupacion-clases";

    public void reportarOcupacion(Long claseId, int valor) {
        OcupacionClase dto = new OcupacionClase(claseId, valor);
        kafkaTemplateOcupacion.send("ocupacion-clases", dto);
        System.out.println("Evento de ocupación enviado " + dto);
    }
}

