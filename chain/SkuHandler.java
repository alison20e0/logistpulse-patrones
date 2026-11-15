package chain;

public class SkuHandler extends Manejador {
    protected void validar(Solicitud s, Bodega b) {
        if (!b.existeSku(s.sku())) rechazar("SKU inexistente: " + s.sku());
        if (s.cantidad() <= 0) rechazar("La cantidad debe ser mayor a 0");
    }
}
