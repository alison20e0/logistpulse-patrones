package logistpulse.strategy;

/** Min-Max: si el stock cae al minimo, se pide hasta llegar al maximo. */
public class MinMaxPolicy implements ReplenishmentPolicy {
    private final int min;
    private final int max;

    public MinMaxPolicy(int min, int max) { this.min = min; this.max = max; }
    public String nombre() { return "Min-Max (" + min + "-" + max + ")"; }
    public int cantidadAPedir(int stockActual, int demandaAnual) {
        return stockActual <= min ? max - stockActual : 0;
    }
}
