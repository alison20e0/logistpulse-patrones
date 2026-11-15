package strategy;

import java.util.Comparator;
import java.util.List;

/** First Expired, First Out. */
public class FefoPolicy implements PickingPolicy {
    public String nombre() { return "FEFO"; }
    public List<Lote> ordenar(List<Lote> lotes) {
        return lotes.stream().sorted(Comparator.comparing(Lote::getVence)).toList();
    }
}
