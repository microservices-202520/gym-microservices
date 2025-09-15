package co.analisys.gimnasio.equipo.service;

import co.analisys.gimnasio.equipo.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class HorarioSubscriber {
    private static final Logger log = LoggerFactory.getLogger(HorarioSubscriber.class);

    @RabbitListener(queues = RabbitMQConfig.HORARIOS_QUEUE)
    public void onCambioHorario(String mensaje) {
        log.info("Cambio de horario recibido: {}", mensaje);
    }
}


