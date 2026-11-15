package logistpulse.strategy;

/** EOQ = raíz(2 * D * S / H). */
public class EoqPolicy implements ReplenishmentPolicy {
    private final double costoPedido;       // S
    private final double costoMantenerUnid; // H

    public EoqPolicy(double costoPedido, double costoMantenerUnid) {
        this.costoPedido = costoPedido;
        this.costoMantenerUnid = costoMantenerUnid;
    }
    public String nombre() { return "EOQ"; }
    public int cantidadAPedir(int stockActual, int demandaAnual) {
        return (int) Math.round(Math.sqrt(2.0 * demandaAnual * costoPedido / costoMantenerUnid));
    }
}
