package co.analisys.gimnasio.miembro.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PAGOS_QUEUE = "pagos-queue";
    public static final String PAGOS_DLQ = "pagos-dlq";

    public static final String NOTIFICACIONES_EXCHANGE = "notificaciones.exchange";

    @Bean
    public Queue pagosQueue() {
        return QueueBuilder.durable(PAGOS_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", PAGOS_DLQ)
                .withArgument("x-message-ttl", 30000)
                .build();
    }

    @Bean
    public Queue pagosDLQ() {
        return QueueBuilder.durable(PAGOS_DLQ).build();
    }

    @Bean
    public FanoutExchange notificacionesExchange() {
        return new FanoutExchange(NOTIFICACIONES_EXCHANGE, true, false);
    }
}


