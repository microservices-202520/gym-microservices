package co.analisys.gimnasio.entrenador.service;

import co.analisys.gimnasio.entrenador.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificacionSubscriber {
    private static final Logger log = LoggerFactory.getLogger(NotificacionSubscriber.class);

    @RabbitListener(queues = RabbitMQConfig.NOTIFICACIONES_QUEUE)
    public void onNuevaInscripcion(String mensaje) {
        log.info("Notificacion recibida: {}", mensaje);
    }
}


