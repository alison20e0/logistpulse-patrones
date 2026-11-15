package strategy;

import java.time.LocalDate;

public class Lote {
    private final String id; private int cantidad;
    private final LocalDate ingreso; private final LocalDate vence;

    public Lote(String id, int cantidad, LocalDate ingreso, LocalDate vence) {
        this.id = id; this.cantidad = cantidad; this.ingreso = ingreso; this.vence = vence;
    }
    public String getId() { return id; }
    public int getCantidad() { return cantidad; }
    public LocalDate getIngreso() { return ingreso; }
    public LocalDate getVence() { return vence; }
    public void quitar(int n) { cantidad -= n; }
    public void agregar(int n) { cantidad += n; }
}
