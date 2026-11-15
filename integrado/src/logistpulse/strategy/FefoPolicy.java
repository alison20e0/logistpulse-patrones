package logistpulse.strategy;

import java.util.Comparator;
import java.util.List;
import logistpulse.model.Lote;

/** First Expired, First Out: sale primero lo que vence antes. */
public class FefoPolicy implements PickingPolicy {
    public String nombre() { return "FEFO"; }
    public List<Lote> ordenar(List<Lote> lotes) {
        return lotes.stream().sorted(Comparator.comparing(Lote::getFechaVencimiento)).toList();
    }
}
