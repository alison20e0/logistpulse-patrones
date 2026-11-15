package observer;

import java.util.ArrayList;
import java.util.List;

/** Suscriptor 3: registro de auditoria de todos los cambios. */
public class LedgerListener implements StockListener {
    private final List<String> registros = new ArrayList<>();

    public void onStockChanged(StockChanged e) {
        registros.add(e.sku() + ": " + e.anterior() + " -> " + e.nuevo() + " (" + e.motivo() + ")");
    }
    public List<String> registros() { return registros; }
}
