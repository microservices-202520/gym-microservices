package co.analisys.gimnasio.entrenador.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String NOTIFICACIONES_EXCHANGE = "notificaciones.exchange";
    public static final String NOTIFICACIONES_QUEUE = "notificaciones.queue";

    @Bean
    public FanoutExchange notificacionesExchange() {
        return new FanoutExchange(NOTIFICACIONES_EXCHANGE, true, false);
    }

    @Bean
    public Queue notificacionesQueue() {
        return new Queue(NOTIFICACIONES_QUEUE, true);
    }

    @Bean
    public Binding notificacionesBinding(FanoutExchange notificacionesExchange, Queue notificacionesQueue) {
        return BindingBuilder.bind(notificacionesQueue).to(notificacionesExchange);
    }
}


