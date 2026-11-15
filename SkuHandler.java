package logistpulse.chain;

import logistpulse.model.Inventario;
import logistpulse.model.ReservaRequest;

public class SkuHandler extends ValidationHandler {
    protected void validar(ReservaRequest req, Inventario inv) {
        if (!inv.existeSku(req.sku())) rechazar("SKU inexistente: " + req.sku());
        if (req.cantidad() <= 0) rechazar("La cantidad debe ser mayor a 0");
        if (req.solicitante() == null || req.solicitante().isBlank()) rechazar("Debe indicar el solicitante");
    }
}
