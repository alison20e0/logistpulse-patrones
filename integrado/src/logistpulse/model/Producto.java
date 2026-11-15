package logistpulse.model;

/** Un producto del catalogo (identificado por su SKU). */
public record Producto(String sku, String nombre, int demandaAnual) {}
