package co.analisys.gimnasio.clase.streams;

import co.analisys.gimnasio.clase.dto.OcupacionClase;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Consumed;

@Configuration
public class OcupacionClaseStream {

    @Bean
    public KStream<String, OcupacionClase> procesarOcupacion(StreamsBuilder builder) {
        KStream<String, OcupacionClase> stream = builder.stream(
                "ocupacion-clases",
                Consumed.with(Serdes.String(), new JsonSerde<>(OcupacionClase.class))
        );

        stream.foreach((key, value) ->
                System.out.println("STREAM >> Clase: " + value.getClaseId() +
                        " → Ocupación: " + value.getOcupacion())
        );

        return stream;
    }
}
