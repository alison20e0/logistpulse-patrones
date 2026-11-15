package observer;

/** Suscriptor 2: avisa a PULSEFLEET para que planifique el transporte. */
public class PulseFleetListener implements StockListener {
    public void onStockChanged(StockChanged e) {
        System.out.println("  [PULSEFLEET] disponibilidad de " + e.sku() + " actualizada a " + e.nuevo());
    }
}
