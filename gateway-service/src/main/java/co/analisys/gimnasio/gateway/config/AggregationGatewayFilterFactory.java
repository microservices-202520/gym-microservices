package co.analisys.gimnasio.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class AggregationGatewayFilterFactory extends AbstractGatewayFilterFactory<AggregationGatewayFilterFactory.Config> {

    private final WebClient webClient;
    private final DiscoveryClient discoveryClient;

    public AggregationGatewayFilterFactory(DiscoveryClient discoveryClient) {
        super(Config.class);
        this.webClient = WebClient.builder().build();
        this.discoveryClient = discoveryClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().value();
            log.info("Procesando ruta de agregación: {}", path);
            
            // Extraer el ID del miembro del path: /api/miembros/{id}/agregado
            String[] pathSegments = path.split("/");
            
            if (pathSegments.length < 4 || !pathSegments[pathSegments.length - 1].equals("agregado")) {
                log.error("Ruta inválida para agregación: {}", path);
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                return response.setComplete();
            }
            
            String miembroId = pathSegments[pathSegments.length - 2]; // Penúltimo elemento
            log.info("Agregando información para miembro: {}", miembroId);

            // Usar service discovery para obtener instancias de servicios
            String miembroServiceUrl = getServiceUrl("miembro-service");
            String claseServiceUrl = getServiceUrl("clase-service");

            // Obtener información del miembro
            Mono<String> miembroInfo = webClient.get()
                    .uri(miembroServiceUrl + "/api/miembros/" + miembroId)
                    .header("Authorization", exchange.getRequest().getHeaders().getFirst("Authorization"))
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnSuccess(response -> log.info("Respuesta miembro service: {}", response))
                    .onErrorReturn("{\"error\": \"No se pudo obtener información del miembro\"}");

            // Obtener clases del miembro
            Mono<String> clasesInfo = webClient.get()
                    .uri(claseServiceUrl + "/api/clases/miembro/" + miembroId)
                    .header("Authorization", exchange.getRequest().getHeaders().getFirst("Authorization"))
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnSuccess(response -> log.info("Respuesta clase service: {}", response))
                    .onErrorReturn("[{\"error\": \"No se pudieron obtener las clases\"}]");

            // Combinar la información
            return Mono.zip(miembroInfo, clasesInfo)
                    .map(tuple -> {
                        String miembro = tuple.getT1();
                        String clases = tuple.getT2();
                        
                        // Crear respuesta agregada (JSON simple)
                        String aggregatedResponse = String.format(
                            "{\"miembro\": %s, \"clases\": %s, \"timestamp\": \"%s\"}", 
                            miembro, clases, java.time.LocalDateTime.now()
                        );
                        
                        return aggregatedResponse;
                    })
                    .flatMap(aggregatedResponse -> {
                        ServerHttpResponse response = exchange.getResponse();
                        response.setStatusCode(HttpStatus.OK);
                        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                        
                        DataBuffer buffer = response.bufferFactory().wrap(aggregatedResponse.getBytes(StandardCharsets.UTF_8));
                        return response.writeWith(Mono.just(buffer));
                    })
                    .onErrorResume(error -> {
                        log.error("Error en agregación: ", error);
                        ServerHttpResponse response = exchange.getResponse();
                        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                        
                        String errorResponse = "{\"error\": \"Error interno del servidor\"}";
                        DataBuffer buffer = response.bufferFactory().wrap(errorResponse.getBytes(StandardCharsets.UTF_8));
                        return response.writeWith(Mono.just(buffer));
                    });
        };
    }
    
    private String getServiceUrl(String serviceName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        if (instances.isEmpty()) {
            log.warn("No hay instancias disponibles para el servicio: {}", serviceName);
            return "http://localhost:8080"; // Fallback
        }
        
        ServiceInstance instance = instances.get(0); // Simple round-robin podría implementarse aquí
        String url = String.format("http://%s:%d", instance.getHost(), instance.getPort());
        log.info("URL resuelva para {}: {}", serviceName, url);
        return url;
    }

    public static class Config {
        // Configuración del filtro si es necesaria
    }
}