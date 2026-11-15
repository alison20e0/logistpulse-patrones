package logistpulse.strategy;

/** Punto de reorden: si el stock cae al punto, se pide un lote fijo. */
public class ReorderPointPolicy implements ReplenishmentPolicy {
    private final int puntoReorden;
    private final int loteFijo;

    public ReorderPointPolicy(int puntoReorden, int loteFijo) {
        this.puntoReorden = puntoReorden;
        this.loteFijo = loteFijo;
    }
    public String nombre() { return "Punto de reorden"; }
    public int cantidadAPedir(int stockActual, int demandaAnual) {
        return stockActual <= puntoReorden ? loteFijo : 0;
    }
}
