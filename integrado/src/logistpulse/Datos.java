package logistpulse;

import java.time.LocalDate;
import logistpulse.chain.*;
import logistpulse.model.*;
import logistpulse.strategy.Picking;

/** Datos ficticios de ejemplo y armado de la cadena de validacion. */
public final class Datos {
    private Datos() {}

    public static Inventario crearInventario(Picking picking) {
        LocalDate hoy = LocalDate.now();
        Inventario inv = new Inventario(100, picking);

        inv.agregarProducto(new Producto("SKU-LECHE", "Leche entera 1L", 1200));
        inv.agregarProducto(new Producto("SKU-ARROZ", "Arroz 5kg", 800));
        inv.agregarProducto(new Producto("SKU-YOGUR", "Yogur natural", 600));

        inv.agregarLote(new Lote("L-101", "SKU-LECHE", 30, hoy.minusDays(10), hoy.plusDays(40)));
        inv.agregarLote(new Lote("L-102", "SKU-LECHE", 30, hoy.minusDays(5), hoy.plusDays(15)));
        inv.agregarLote(new Lote("L-103", "SKU-LECHE", 30, hoy.minusDays(1), hoy.plusDays(90)));

        inv.agregarLote(new Lote("L-201", "SKU-ARROZ", 200, hoy.minusDays(30), hoy.plusDays(300)));
        inv.agregarLote(new Lote("L-202", "SKU-ARROZ", 15, hoy.minusDays(2), hoy.plusDays(365)));

        Lote cuarentena = new Lote("L-301", "SKU-YOGUR", 50, hoy.minusDays(3), hoy.plusDays(30));
        cuarentena.setEnCuarentena(true);
        inv.agregarLote(cuarentena);
        inv.agregarLote(new Lote("L-302", "SKU-YOGUR", 20, hoy.minusDays(20), hoy.minusDays(2))); // vencido
        return inv;
    }

    /** SKU -> stock -> lote -> capacidad. */
    public static ValidationHandler crearCadena() {
        ValidationHandler cadena = new SkuHandler();
        cadena.setSiguiente(new StockHandler())
              .setSiguiente(new LoteHandler())
              .setSiguiente(new CapacidadHandler());
        return cadena;
    }
}
