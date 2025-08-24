package co.analisys.gimnasio.clase.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClaseConEntrenadorDto {
    private Long id;
    private String nombre;
    private LocalDateTime horario;
    private int capacidadMaxima;
    private EntrenadorDto entrenador;
}
