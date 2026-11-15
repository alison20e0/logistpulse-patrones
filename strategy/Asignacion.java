package strategy;

public record Asignacion(Lote lote, int cantidad) {
    @Override public String toString() { return lote.getId() + " x" + cantidad; }
}
