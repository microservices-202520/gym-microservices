package co.analisys.gimnasio.miembro.config;

import co.analisys.gimnasio.miembro.dto.CambioHorarioDTO;
import co.analisys.gimnasio.miembro.dto.OcupacionClase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    private static final String BOOTSTRAP_SERVERS = "kafka:9092";

    @Bean
    public ProducerFactory<String, CambioHorarioDTO> producerFactoryCambio() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                org.springframework.kafka.support.serializer.JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, CambioHorarioDTO> kafkaTemplateCambio() {
        return new KafkaTemplate<>(producerFactoryCambio());
    }

    @Bean
    public ConsumerFactory<String, CambioHorarioDTO> consumerFactoryCambio() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "gimnasio-miembros-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                org.springframework.kafka.support.serializer.JsonDeserializer.class);
        config.put(org.springframework.kafka.support.serializer.JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                new org.springframework.kafka.support.serializer.JsonDeserializer<>(CambioHorarioDTO.class, false)
        );
    }

    @Bean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, CambioHorarioDTO> kafkaListenerContainerFactoryCambio() {
        ConcurrentKafkaListenerContainerFactory<String, CambioHorarioDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryCambio());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, OcupacionClase> consumerFactoryOcupacion() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "monitoreo-grupo");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                org.springframework.kafka.support.serializer.JsonDeserializer.class);
        config.put(org.springframework.kafka.support.serializer.JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                new org.springframework.kafka.support.serializer.JsonDeserializer<>(OcupacionClase.class, false)
        );
    }

    @Bean(name = "kafkaListenerContainerFactoryOcupacion")
    public ConcurrentKafkaListenerContainerFactory<String, OcupacionClase> kafkaListenerContainerFactoryOcupacion() {
        ConcurrentKafkaListenerContainerFactory<String, OcupacionClase> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryOcupacion());
        return factory;
    }
}
