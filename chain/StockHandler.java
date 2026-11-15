package chain;

public class StockHandler extends Manejador {
    protected void validar(Solicitud s, Bodega b) {
        if (b.stockTotal(s.sku()) < s.cantidad())
            rechazar("Stock insuficiente: hay " + b.stockTotal(s.sku()) + ", se pidieron " + s.cantidad());
    }
}
