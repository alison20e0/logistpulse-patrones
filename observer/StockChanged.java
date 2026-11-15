package observer;

/** El evento que se publica cuando cambia el stock. */
public record StockChanged(String sku, int anterior, int nuevo, String motivo) {}
