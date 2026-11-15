package observer;

import java.util.*;

/** NOTIFICADOR (publisher): guarda la lista de suscriptores y no conoce su detalle. */
public class Inventario {
    private final Map<String, Integer> stock = new HashMap<>();
    private final List<StockListener> suscriptores = new ArrayList<>();

    public void suscribir(StockListener l) { suscriptores.add(l); }
    public void darDeBaja(StockListener l) { suscriptores.remove(l); }

    public int stock(String sku) { return stock.getOrDefault(sku, 0); }

    public void ajustar(String sku, int delta, String motivo) {
        int anterior = stock(sku);
        int nuevo = anterior + delta;
        if (nuevo < 0) throw new IllegalStateException("Stock negativo en " + sku);
        stock.put(sku, nuevo);
        publicar(new StockChanged(sku, anterior, nuevo, motivo));
    }

    private void publicar(StockChanged evento) {
        for (StockListener l : new ArrayList<>(suscriptores)) l.onStockChanged(evento);
    }
}
