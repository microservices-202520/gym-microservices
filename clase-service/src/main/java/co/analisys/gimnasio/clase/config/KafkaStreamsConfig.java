package co.analisys.gimnasio.clase.config;

import co.analisys.gimnasio.clase.dto.DatosEntrenamiento;
import co.analisys.gimnasio.clase.dto.ResumenEntrenamiento;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfig {

    @Bean
    public KStream<String, DatosEntrenamiento> kStream(StreamsBuilder streamsBuilder) {
        KStream<String, DatosEntrenamiento> stream = streamsBuilder.stream(
                "datos-entrenamiento",
                org.apache.kafka.streams.kstream.Consumed.with(Serdes.String(), new JsonSerde<>(DatosEntrenamiento.class))
        );

        stream.groupByKey()
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofDays(7)))
                .aggregate(
                        ResumenEntrenamiento::new,
                        (key, value, aggregate) -> aggregate.actualizar(value),
                        Materialized.<String, ResumenEntrenamiento, WindowStore<org.apache.kafka.common.utils.Bytes, byte[]>>as("resumen-entrenamiento-store")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(new JsonSerde<>(ResumenEntrenamiento.class))
                )
                .toStream()
                .to("resumen-entrenamiento");

        return stream;
    }
}
