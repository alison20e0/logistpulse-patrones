package logistpulse.strategy;

import java.util.Comparator;
import java.util.List;
import logistpulse.model.Lote;

/** Last In, First Out: sale primero lo que entro al final. */
public class LifoPolicy implements PickingPolicy {
    public String nombre() { return "LIFO"; }
    public List<Lote> ordenar(List<Lote> lotes) {
        return lotes.stream().sorted(Comparator.comparing(Lote::getFechaIngreso).reversed()).toList();
    }
}
