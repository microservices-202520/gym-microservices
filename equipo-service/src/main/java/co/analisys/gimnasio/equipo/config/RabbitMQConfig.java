package co.analisys.gimnasio.equipo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String HORARIOS_EXCHANGE = "clases.horarios.exchange";
    public static final String HORARIOS_QUEUE = "clases.horarios.equipo";

    @Bean
    public FanoutExchange horariosExchange() {
        return new FanoutExchange(HORARIOS_EXCHANGE, true, false);
    }

    @Bean
    public Queue horariosQueue() {
        return new Queue(HORARIOS_QUEUE, true);
    }

    @Bean
    public Binding binding(FanoutExchange horariosExchange, Queue horariosQueue) {
        return BindingBuilder.bind(horariosQueue).to(horariosExchange);
    }
}


