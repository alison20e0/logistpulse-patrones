package observer;

/** OBSERVER: interfaz comun de los suscriptores. */
public interface StockListener {
    void onStockChanged(StockChanged evento);
}
