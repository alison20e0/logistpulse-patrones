package strategy;

import java.util.List;

/** STRATEGY: interfaz comun de las politicas de picking. */
public interface PickingPolicy {
    String nombre();
    List<Lote> ordenar(List<Lote> lotes); // en que orden se consumen los lotes
}
