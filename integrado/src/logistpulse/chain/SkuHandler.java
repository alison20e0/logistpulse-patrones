package logistpulse.chain;

import logistpulse.model.Inventario;
import logistpulse.model.ReservaRequest;

public class SkuHandler extends ValidationHandler {
    protected void validar(ReservaRequest req, Inventario inv) {
        if (req.sku() == null || !inv.existeSku(req.sku()))
            throw new ReservaInvalidaException("SKU inexistente: " + req.sku());
        if (req.cantidad() <= 0)
            throw new ReservaInvalidaException("La cantidad debe ser mayor a 0");
    }
}
