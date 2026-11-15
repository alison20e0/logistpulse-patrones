package strategy;

import java.util.Comparator;
import java.util.List;

/** Last In, First Out. */
public class LifoPolicy implements PickingPolicy {
    public String nombre() { return "LIFO"; }
    public List<Lote> ordenar(List<Lote> lotes) {
        return lotes.stream().sorted(Comparator.comparing(Lote::getIngreso).reversed()).toList();
    }
}
