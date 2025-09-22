package co.analisys.gimnasio.clase.dto;

public class CambioHorarioDTO {
    private Long claseId;
    private String nombre;
    private String nuevoHorario;
    private String accion;

    // Constructores
    public CambioHorarioDTO() {}

    public CambioHorarioDTO(Long claseId, String nombre, String nuevoHorario, String accion) {
        this.claseId = claseId;
        this.nombre = nombre;
        this.nuevoHorario = nuevoHorario;
        this.accion = accion;
    }

    // Getters y Setters
    public Long getClaseId() { return claseId; }
    public void setClaseId(Long claseId) { this.claseId = claseId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNuevoHorario() { return nuevoHorario; }
    public void setNuevoHorario(String nuevoHorario) { this.nuevoHorario = nuevoHorario; }

    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }
}