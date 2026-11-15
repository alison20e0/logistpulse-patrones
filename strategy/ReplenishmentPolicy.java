package strategy;

/** STRATEGY (segunda familia): cuanto reponer. */
public interface ReplenishmentPolicy {
    String nombre();
    int cantidadAPedir(int stockActual, int demandaAnual);
}
