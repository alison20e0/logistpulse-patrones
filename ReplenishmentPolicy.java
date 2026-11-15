package logistpulse.strategy;

/** STRATEGY (segunda familia): politica de reposicion. Devuelve cuanto pedir. */
public interface ReplenishmentPolicy {
    String nombre();
    int cantidadAPedir(int stockActual, int demandaAnual);
}
