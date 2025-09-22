package co.analisys.gimnasio.clase.dto;

public class DatosEntrenamiento {
    private Long miembroId;
    private String tipoEjercicio;
    private int duracionMin; // duración en minutos

    public DatosEntrenamiento() {}

    public DatosEntrenamiento(Long miembroId, String tipoEjercicio, int duracionMin) {
        this.miembroId = miembroId;
        this.tipoEjercicio = tipoEjercicio;
        this.duracionMin = duracionMin;
    }

    // getters y setters

    public Long getMiembroId() {
        return miembroId;
    }

    public void setMiembroId(Long miembroId) {
        this.miembroId = miembroId;
    }

    public String getTipoEjercicio() {
        return tipoEjercicio;
    }

    public void setTipoEjercicio(String tipoEjercicio) {
        this.tipoEjercicio = tipoEjercicio;
    }

    public int getDuracionMin() {
        return duracionMin;
    }

    public void setDuracionMin(int duracionMin) {
        this.duracionMin = duracionMin;
    }
}
