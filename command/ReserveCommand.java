package command;

/** Comando concreto: reservar. Su inverso es liberar. */
public class ReserveCommand implements Command {
    private final String id; private final Inventory inv; private final String sku; private final int cantidad;

    public ReserveCommand(String id, Inventory inv, String sku, int cantidad) {
        this.id = id; this.inv = inv; this.sku = sku; this.cantidad = cantidad;
    }
    public String id() { return id; }
    public void execute() { inv.reservar(sku, cantidad); }
    public void undo() { inv.liberar(sku, cantidad); }
    public String descripcion() { return "RESERVE " + sku + " x" + cantidad; }
}
