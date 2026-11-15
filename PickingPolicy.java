package logistpulse.strategy;

import java.util.List;
import logistpulse.model.Lote;

/** STRATEGY: interfaz comun de las politicas de picking. */
public interface PickingPolicy {
    String nombre();
    List<Lote> ordenar(List<Lote> lotes);
}
