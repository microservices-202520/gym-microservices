package co.analisys.gimnasio.miembro.controller;

import co.analisys.gimnasio.miembro.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class PagoTestController {

    private final RabbitTemplate rabbitTemplate;

    public PagoTestController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Encola un pago con monto=0 para provocar fallo y envío a DLQ
    @PostMapping("/pago-fallo")
    public ResponseEntity<Void> publicarPagoFallo() {
        String pagoJson = "{\"miembroId\":0,\"concepto\":\"TEST_FALLO\",\"monto\":0}";
        rabbitTemplate.convertAndSend("", RabbitMQConfig.PAGOS_QUEUE, pagoJson);
        return ResponseEntity.accepted().build();
    }
}


