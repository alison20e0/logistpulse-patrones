package command;

/** Comando concreto: ajuste de inventario (conteo fisico, merma). Su inverso es el ajuste contrario. */
public class AdjustCommand implements Command {
    private final String id; private final Inventory inv; private final String sku; private final int delta;

    public AdjustCommand(String id, Inventory inv, String sku, int delta) {
        this.id = id; this.inv = inv; this.sku = sku; this.delta = delta;
    }
    public String id() { return id; }
    public void execute() { inv.ajustar(sku, delta); }
    public void undo() { inv.ajustar(sku, -delta); }
    public String descripcion() { return "ADJUST " + sku + " " + (delta >= 0 ? "+" : "") + delta; }
}
