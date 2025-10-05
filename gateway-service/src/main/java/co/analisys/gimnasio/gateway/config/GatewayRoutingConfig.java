package co.analisys.gimnasio.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutingConfig {

    private final AggregationGatewayFilterFactory aggregationFilter;
    
    public GatewayRoutingConfig(AggregationGatewayFilterFactory aggregationFilter) {
        this.aggregationFilter = aggregationFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Ruta de agregación especial - debe ir ANTES que las rutas individuales
                .route("miembro-aggregation", r -> r
                        .path("/api/miembros/*/agregado")
                        .filters(f -> f.filter(aggregationFilter.apply(new AggregationGatewayFilterFactory.Config())))
                        .uri("no://op")) // URI dummy, no se usa porque el filtro maneja la respuesta
                
                // Miembro Service Routes con Load Balancing
                .route("miembro-service", r -> r
                        .path("/api/miembros/**")
                        .uri("lb://miembro-service"))
                
                // Entrenador Service Routes con Load Balancing
                .route("entrenador-service", r -> r
                        .path("/api/entrenadores/**")
                        .uri("lb://entrenador-service"))
                
                // Clase Service Routes con Load Balancing
                .route("clase-service", r -> r
                        .path("/api/clases/**")
                        .uri("lb://clase-service"))
                
                // Equipo Service Routes con Load Balancing
                .route("equipo-service", r -> r
                        .path("/api/equipos/**")
                        .uri("lb://equipo-service"))
                
                .build();
    }
}