package command;

/** Comando concreto: liberar una reserva. Su inverso es reservar de nuevo. */
public class ReleaseCommand implements Command {
    private final String id; private final Inventory inv; private final String sku; private final int cantidad;

    public ReleaseCommand(String id, Inventory inv, String sku, int cantidad) {
        this.id = id; this.inv = inv; this.sku = sku; this.cantidad = cantidad;
    }
    public String id() { return id; }
    public void execute() { inv.liberar(sku, cantidad); }
    public void undo() { inv.reservar(sku, cantidad); }
    public String descripcion() { return "RELEASE " + sku + " x" + cantidad; }
}
