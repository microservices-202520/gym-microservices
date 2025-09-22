package co.analisys.gimnasio.clase.dto;

public class ResumenEntrenamiento {
    private int totalMin;

    public ResumenEntrenamiento() {
        this.totalMin = 0;
    }

    public ResumenEntrenamiento actualizar(DatosEntrenamiento dato) {
        this.totalMin += dato.getDuracionMin();
        return this;
    }

    public int getTotalMin() {
        return totalMin;
    }
}
