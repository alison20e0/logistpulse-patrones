package logistpulse.strategy;

/** EOQ = raiz(2 * D * S / H). Siempre recomienda el tamano de pedido economico. */
public class EoqPolicy implements ReplenishmentPolicy {
    private final double costoPedido;
    private final double costoMantenerUnid;

    public EoqPolicy(double costoPedido, double costoMantenerUnid) {
        this.costoPedido = costoPedido;
        this.costoMantenerUnid = costoMantenerUnid;
    }
    public String nombre() { return "EOQ"; }
    public int cantidadAPedir(int stockActual, int demandaAnual) {
        return (int) Math.round(Math.sqrt(2.0 * demandaAnual * costoPedido / costoMantenerUnid));
    }
}
