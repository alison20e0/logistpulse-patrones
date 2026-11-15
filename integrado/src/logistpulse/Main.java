package logistpulse;

import java.time.LocalDate;
import logistpulse.chain.*;
import logistpulse.command.*;
import logistpulse.model.*;
import logistpulse.strategy.*;

public class Main {
    public static void main(String[] args) {
        LocalDate hoy = LocalDate.now();

        // --- Strategy: contexto Picking con política inicial FEFO ---
        Picking picking = new Picking(new FefoPolicy());
        Inventario inv = new Inventario(100, picking);

        // Tres lotes del mismo SKU: distinto ingreso y distinto vencimiento
        inv.agregarLote(new Lote("L-A", "SKU-LECHE", 30, hoy.minusDays(10), hoy.plusDays(40)));
        inv.agregarLote(new Lote("L-B", "SKU-LECHE", 30, hoy.minusDays(5),  hoy.plusDays(15)));
        inv.agregarLote(new Lote("L-C", "SKU-LECHE", 30, hoy.minusDays(1),  hoy.plusDays(90)));
        Lote cuarentena = new Lote("L-Q", "SKU-YOGUR", 50, hoy.minusDays(3), hoy.plusDays(30));
        cuarentena.setEnCuarentena(true);
        inv.agregarLote(cuarentena);

        // --- Chain of Responsibility: SKU -> stock -> lote -> capacidad ---
        ValidationHandler cadena = new SkuHandler();
        cadena.setSiguiente(new StockHandler())
              .setSiguiente(new LoteHandler())
              .setSiguiente(new CapacidadHandler());

        CommandInvoker invoker = new CommandInvoker();

        System.out.println("=== 1. Command + Chain: reservas validas e invalidas ===");
        intentar(invoker, new ReserveCommand(inv, new ReservaRequest("SKU-LECHE", 40), cadena));
        intentar(invoker, new ReserveCommand(inv, new ReservaRequest("SKU-XXX", 5), cadena));
        intentar(invoker, new ReserveCommand(inv, new ReservaRequest("SKU-LECHE", 500), cadena));
        intentar(invoker, new ReserveCommand(inv, new ReservaRequest("SKU-YOGUR", 10), cadena));

        System.out.println("\n=== 2. Command: deshacer la ultima reserva ===");
        System.out.println("Stock antes de undo: " + inv.stockTotal("SKU-LECHE"));
        invoker.deshacerUltimo();
        System.out.println("Stock despues de undo: " + inv.stockTotal("SKU-LECHE"));

        System.out.println("\n=== 3. Strategy: misma reserva, distinta politica ===");
        for (PickingPolicy p : new PickingPolicy[]{new FefoPolicy(), new FifoPolicy(), new LifoPolicy()}) {
            picking.setPolitica(p); // cambio en tiempo de ejecucion
            ReserveCommand cmd = new ReserveCommand(inv, new ReservaRequest("SKU-LECHE", 40), cadena);
            cmd.execute();
            System.out.println(p.nombre() + " -> " + cmd.descripcion());
            cmd.undo(); // devolvemos para comparar en igualdad de condiciones
        }

        System.out.println("\n=== 4. Strategy (reposicion) ===");
        for (ReplenishmentPolicy r : new ReplenishmentPolicy[]{new ReorderPointPolicy(20, 100), new EoqPolicy(50, 2)}) {
            System.out.println(r.nombre() + " -> pedir " + r.cantidadAPedir(15, 1200) + " u.");
        }

        System.out.println("\n=== Ledger de auditoria ===");
        invoker.getLedger().forEach(System.out::println);
    }

    private static void intentar(CommandInvoker invoker, Command cmd) {
        try {
            invoker.ejecutar(cmd);
            System.out.println("OK: " + cmd.descripcion());
        } catch (ReservaInvalidaException e) {
            System.out.println("RECHAZADA: " + e.getMessage());
        }
    }
}
