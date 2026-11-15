package logistpulse.strategy;

import java.util.Comparator;
import java.util.List;
import logistpulse.model.Lote;

/** First In, First Out: sale primero lo que entró antes. */
public class FifoPolicy implements PickingPolicy {
    public String nombre() { return "FIFO"; }
    public List<Lote> ordenar(List<Lote> lotes) {
        return lotes.stream().sorted(Comparator.comparing(Lote::getFechaIngreso)).toList();
    }
}
