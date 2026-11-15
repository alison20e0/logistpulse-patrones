package chain;

public class LoteHandler extends Manejador {
    protected void validar(Solicitud s, Bodega b) {
        if (b.stockUtilizable(s.sku()) < s.cantidad())
            rechazar("Lotes vencidos o en cuarentena: solo " + b.stockUtilizable(s.sku()) + " u. utilizables");
    }
}
