package strategy;

/** EOQ = raiz(2 * D * S / H). */
public class EoqPolicy implements ReplenishmentPolicy {
    private final double costoPedido, costoMantener;
    public EoqPolicy(double costoPedido, double costoMantener) { this.costoPedido = costoPedido; this.costoMantener = costoMantener; }
    public String nombre() { return "EOQ"; }
    public int cantidadAPedir(int stockActual, int demandaAnual) {
        return (int) Math.round(Math.sqrt(2.0 * demandaAnual * costoPedido / costoMantener));
    }
}
