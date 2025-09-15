package co.analisys.gimnasio.miembro.service;

import co.analisys.gimnasio.miembro.config.RabbitMQConfig;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PagoService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(PagoService.class);

    @RabbitListener(queues = RabbitMQConfig.PAGOS_QUEUE)
    public void procesarPago(String pagoJson) {
        try {
            log.info("[PagoService] Mensaje recibido: {}", pagoJson);
            boolean exito = procesarPagoInterno(pagoJson);
            if (!exito) {
                log.warn("[PagoService] Pago marcado como FALLIDO, se rechazará y enviará a DLQ");
                throw new RuntimeException("Fallo en el procesamiento del pago");
            }
            log.info("[PagoService] Pago procesado OK");
        } catch (Exception e) {
            log.error("[PagoService] Rechazando mensaje para DLQ: {}", e.getMessage());
            throw new AmqpRejectAndDontRequeueException("Error en el pago, enviando a DLQ", e);
        }
    }

    private boolean procesarPagoInterno(String pagoJson) {
        try {
            JsonNode root = objectMapper.readTree(pagoJson);
            int monto = root.path("monto").asInt(0);
            String concepto = root.path("concepto").asText("");
            log.info("[PagoService] Validando pago: concepto={}, monto={}", concepto, monto);
            // Forzar fallo si monto==0 o concepto==TEST_FALLO
            if (monto == 0 || "TEST_FALLO".equalsIgnoreCase(concepto)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            // Si el JSON es inválido, lo tratamos como fallo para enviar a DLQ
            log.warn("[PagoService] JSON invalido, se forzará DLQ: {}", e.getMessage());
            return false;
        }
    }
}


