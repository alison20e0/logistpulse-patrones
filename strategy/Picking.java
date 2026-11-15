package strategy;

import java.util.ArrayList;
import java.util.List;

/** CONTEXTO del Strategy: delega el orden en la politica activa sin conocer sus detalles. */
public class Picking {
    private PickingPolicy politica;

    public Picking(PickingPolicy politica) { this.politica = politica; }
    public void setPolitica(PickingPolicy politica) { this.politica = politica; } // cambio en ejecucion
    public PickingPolicy getPolitica() { return politica; }

    public List<Asignacion> seleccionar(List<Lote> lotes, int cantidad) {
        List<Asignacion> resultado = new ArrayList<>();
        int pendiente = cantidad;
        for (Lote lote : politica.ordenar(lotes)) {
            if (pendiente == 0) break;
            int tomar = Math.min(lote.getCantidad(), pendiente);
            if (tomar > 0) { resultado.add(new Asignacion(lote, tomar)); pendiente -= tomar; }
        }
        return resultado;
    }
}
