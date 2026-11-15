package logistpulse.model;

import java.time.LocalDate;

/** Un lote de un SKU dentro de la bodega. */
public class Lote {
    private final String id;
    private final String sku;
    private int cantidad;
    private final LocalDate fechaIngreso;
    private final LocalDate fechaVencimiento;
    private boolean enCuarentena;

    public Lote(String id, String sku, int cantidad, LocalDate fechaIngreso, LocalDate fechaVencimiento) {
        this.id = id;
        this.sku = sku;
        this.cantidad = cantidad;
        this.fechaIngreso = fechaIngreso;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getId() { return id; }
    public String getSku() { return sku; }
    public int getCantidad() { return cantidad; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public boolean isEnCuarentena() { return enCuarentena; }
    public void setEnCuarentena(boolean enCuarentena) { this.enCuarentena = enCuarentena; }

    public void quitar(int n) { this.cantidad -= n; }
    public void agregar(int n) { this.cantidad += n; }

    public boolean estaVencido() { return fechaVencimiento.isBefore(LocalDate.now()); }
    /** Un lote es utilizable si no está vencido ni en cuarentena. */
    public boolean esUtilizable() { return !estaVencido() && !enCuarentena; }

    @Override
    public String toString() {
        return id + "(" + cantidad + " u., vence " + fechaVencimiento + ")";
    }
}
