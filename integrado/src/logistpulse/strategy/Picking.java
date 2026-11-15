package logistpulse.strategy;

import java.util.ArrayList;
import java.util.List;
import logistpulse.model.Asignacion;
import logistpulse.model.Lote;

/** STRATEGY: el CONTEXTO. Delega el orden en la política activa sin conocer su detalle. */
public class Picking {
    private PickingPolicy politica;

    public Picking(PickingPolicy politica) { this.politica = politica; }

    /** Cambia el algoritmo en tiempo de ejecución. */
    public void setPolitica(PickingPolicy politica) { this.politica = politica; }
    public PickingPolicy getPolitica() { return politica; }

    public List<Asignacion> seleccionar(List<Lote> lotesUtilizables, int cantidad) {
        List<Asignacion> resultado = new ArrayList<>();
        int pendiente = cantidad;
        for (Lote lote : politica.ordenar(lotesUtilizables)) {
            if (pendiente == 0) break;
            int tomar = Math.min(lote.getCantidad(), pendiente);
            if (tomar > 0) {
                resultado.add(new Asignacion(lote, tomar));
                pendiente -= tomar;
            }
        }
        return resultado;
    }
}
