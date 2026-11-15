package logistpulse.strategy;

/** STRATEGY (segunda familia): política de reposición. Devuelve cuánto pedir. */
public interface ReplenishmentPolicy {
    String nombre();
    int cantidadAPedir(int stockActual, int demandaAnual);
}
