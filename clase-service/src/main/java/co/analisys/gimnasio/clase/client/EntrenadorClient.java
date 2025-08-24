package co.analisys.gimnasio.clase.client;

import co.analisys.gimnasio.clase.dto.EntrenadorDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EntrenadorClient {
    
    @Value("${entrenador.service.url:http://localhost:8082}")
    private String entrenadorServiceUrl;
    
    private final RestTemplate restTemplate;
    
    public EntrenadorClient() {
        this.restTemplate = new RestTemplate();
    }
    
    public EntrenadorDto obtenerEntrenadorPorId(Long entrenadorId) {
        try {
            String url = entrenadorServiceUrl + "/api/entrenadores/" + entrenadorId;
            return restTemplate.getForObject(url, EntrenadorDto.class);
        } catch (Exception e) {
            System.out.println("Error al obtener entrenador: " + e.getMessage());
            return null;
        }
    }
}
