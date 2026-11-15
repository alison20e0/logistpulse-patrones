package logistpulse.chain;

import logistpulse.model.Inventario;
import logistpulse.model.ReservaRequest;

public class StockHandler extends ValidationHandler {
    protected void validar(ReservaRequest req, Inventario inv) {
        int total = inv.stockTotal(req.sku());
        if (total < req.cantidad())
            throw new ReservaInvalidaException(
                "Stock insuficiente de " + req.sku() + ": hay " + total + ", se pidieron " + req.cantidad());
    }
}
