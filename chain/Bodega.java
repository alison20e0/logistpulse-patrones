package chain;

import java.util.*;

public class Bodega {
    private final List<Lote> lotes = new ArrayList<>();
    private final int capacidadMaximaPorReserva;

    public Bodega(int capacidadMaximaPorReserva) { this.capacidadMaximaPorReserva = capacidadMaximaPorReserva; }

    public void agregar(Lote l) { lotes.add(l); }
    public boolean existeSku(String sku) { return lotes.stream().anyMatch(l -> l.sku().equals(sku)); }
    public int stockTotal(String sku) {
        return lotes.stream().filter(l -> l.sku().equals(sku)).mapToInt(Lote::cantidad).sum();
    }
    public int stockUtilizable(String sku) {
        return lotes.stream().filter(l -> l.sku().equals(sku) && l.utilizable()).mapToInt(Lote::cantidad).sum();
    }
    public int capacidadMaxima() { return capacidadMaximaPorReserva; }
}
