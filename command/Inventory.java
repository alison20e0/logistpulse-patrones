package command;

import java.util.HashMap;
import java.util.Map;

/** RECEIVER: el objeto de negocio que realmente hace el trabajo. */
public class Inventory {
    private final Map<String, Integer> stock = new HashMap<>();

    public void recibir(String sku, int n) { stock.merge(sku, n, Integer::sum); }
    public int stock(String sku) { return stock.getOrDefault(sku, 0); }

    public void reservar(String sku, int n) {
        if (stock(sku) < n)
            throw new IllegalStateException("Stock insuficiente de " + sku + ": hay " + stock(sku) + ", se pidieron " + n);
        stock.put(sku, stock(sku) - n);
    }
    public void liberar(String sku, int n) { stock.merge(sku, n, Integer::sum); }
    public void ajustar(String sku, int delta) {
        if (stock(sku) + delta < 0) throw new IllegalStateException("El ajuste dejaria stock negativo en " + sku);
        stock.merge(sku, delta, Integer::sum);
    }
}
