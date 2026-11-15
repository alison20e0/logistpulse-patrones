package strategy;

import java.util.Comparator;
import java.util.List;

/** First In, First Out. */
public class FifoPolicy implements PickingPolicy {
    public String nombre() { return "FIFO"; }
    public List<Lote> ordenar(List<Lote> lotes) {
        return lotes.stream().sorted(Comparator.comparing(Lote::getIngreso)).toList();
    }
}
