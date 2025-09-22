package co.analisys.gimnasio.clase.dto;

public class OcupacionClase {
    private Long claseId;
    private int ocupacion;

    public OcupacionClase() {}

    public OcupacionClase(Long claseId, int ocupacion) {
        this.claseId = claseId;
        this.ocupacion = ocupacion;
    }

    public Long getClaseId() { return claseId; }
    public void setClaseId(Long claseId) { this.claseId = claseId; }

    public int getOcupacion() { return ocupacion; }
    public void setOcupacion(int ocupacion) { this.ocupacion = ocupacion; }
}
