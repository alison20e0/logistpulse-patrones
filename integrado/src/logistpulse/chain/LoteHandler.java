package logistpulse.chain;

import logistpulse.model.Inventario;
import logistpulse.model.ReservaRequest;

/** Descarta lotes vencidos o en cuarentena: solo cuenta el stock utilizable. */
public class LoteHandler extends ValidationHandler {
    protected void validar(ReservaRequest req, Inventario inv) {
        int utilizable = inv.stockUtilizable(req.sku());
        if (utilizable < req.cantidad())
            throw new ReservaInvalidaException(
                "Lotes vencidos o en cuarentena: solo " + utilizable + " u. utilizables de " + req.sku());
    }
}
