package co.analisys.gimnasio.clase.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Microservicio Clase - API",
                description = "API REST para la gestión de clases en el gimnasio. " +
                        "Permite programar, consultar, actualizar y eliminar clases. " +
                        "Esta API requiere autenticación JWT con roles específicos para la mayoría de operaciones.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Equipo de Desarrollo",
                        email = "desarrollo@analisys.co",
                        url = "https://analisys.co"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Servidor de Desarrollo",
                        url = "http://localhost:8083"
                ),
                @Server(
                        description = "Servidor de Producción",
                        url = "https://api-clase.gimnasio.analisys.co"
                )
        },
        security = @SecurityRequirement(name = "Bearer Authentication")
)
@SecurityScheme(
        name = "Bearer Authentication",
        description = "Autenticación JWT Bearer Token requerida para acceder a los endpoints protegidos. " +
                "Los tokens deben ser obtenidos de Keycloak y contener los roles necesarios (ROLE_ADMIN o ROLE_USER).",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Microservicio Clase - API")
                        .description("API REST completa para la gestión de clases en el gimnasio. " +
                                "Incluye endpoints para programar, consultar, actualizar y eliminar clases. " +
                                "Todos los endpoints protegidos requieren autenticación JWT con roles específicos.")
                        .version("1.0.0")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Equipo de Desarrollo")
                                .email("desarrollo@analisys.co"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement()
                        .addList("Bearer Authentication"));
    }
}
