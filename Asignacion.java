package logistpulse.model;

/** Cuantas unidades se sacaron de un lote concreto en una reserva. */
public record Asignacion(Lote lote, int cantidad) {
    @Override
    public String toString() { return lote.getId() + " x" + cantidad; }
}
