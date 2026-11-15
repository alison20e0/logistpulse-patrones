package observer;

/** Suscriptor 1: sugiere reponer cuando el stock baja del punto de reorden. */
public class ReposicionListener implements StockListener {
    private final int puntoReorden;
    public ReposicionListener(int puntoReorden) { this.puntoReorden = puntoReorden; }

    public void onStockChanged(StockChanged e) {
        if (e.nuevo() <= puntoReorden)
            System.out.println("  [Reposicion] " + e.sku() + " en " + e.nuevo() + " <= " + puntoReorden + ": se recomienda reponer");
    }
}
