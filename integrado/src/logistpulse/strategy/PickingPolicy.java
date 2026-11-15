package logistpulse.strategy;

import java.util.List;
import logistpulse.model.Lote;

/** STRATEGY: interfaz común de las políticas de picking. */
public interface PickingPolicy {
    String nombre();
    /** Devuelve los lotes en el orden en que se deben consumir. */
    List<Lote> ordenar(List<Lote> lotes);
}
