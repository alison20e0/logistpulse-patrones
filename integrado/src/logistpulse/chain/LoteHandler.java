package logistpulse.chain;

import logistpulse.model.Inventario;
import logistpulse.model.Lote;
import logistpulse.model.ReservaRequest;

/** Nunca se usan lotes vencidos o en cuarentena. En modo manual valida el lote indicado. */
public class LoteHandler extends ValidationHandler {
    protected void validar(ReservaRequest req, Inventario inv) {
        if (req.esAuto()) {
            int utilizable = inv.stockUtilizable(req.sku());
            if (utilizable < req.cantidad())
                rechazar("Lotes vencidos o en cuarentena: solo " + utilizable + " u. utilizables de " + req.sku());
            return;
        }
        Lote lote = inv.buscarLote(req.sku(), req.loteId()).orElse(null);
        if (lote == null) rechazar("El lote " + req.loteId() + " no existe para " + req.sku());
        if (!lote.esUtilizable()) rechazar("El lote " + lote.getId() + " esta " + lote.estado());
        if (lote.getCantidad() < req.cantidad())
            rechazar("El lote " + lote.getId() + " solo tiene " + lote.getCantidad() + " u.");
    }
}
