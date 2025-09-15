package co.analisys.gimnasio.clase.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String HORARIOS_EXCHANGE = "clases.horarios.exchange";

    @Bean
    public FanoutExchange horariosExchange() {
        return new FanoutExchange(HORARIOS_EXCHANGE, true, false);
    }
}


