package logistpulse;

import java.util.UUID;
import logistpulse.chain.*;
import logistpulse.command.*;
import logistpulse.model.*;
import logistpulse.strategy.*;

/** Recorrido automatico (sin menu) de Command + Chain + Strategy. */
public class Demo {
    public static void main(String[] args) {
        Picking picking = new Picking(new FefoPolicy());
        Inventario inv = Datos.crearInventario(picking);
        ValidationHandler cadena = Datos.crearCadena();
        ReservaRepositorio repo = new ReservaRepositorio();
        CommandInvoker invoker = new CommandInvoker();

        System.out.println("=== 1. Command + Chain: reservas validas e invalidas ===");
        ReserveCommand ok = new ReserveCommand(inv, repo, new ReservaRequest("SKU-LECHE", 40, "AUTO", "ana"), cadena);
        intentar(invoker, ok);
        intentar(invoker, new ReserveCommand(inv, repo, new ReservaRequest("SKU-XXX", 5, "AUTO", "ana"), cadena));
        intentar(invoker, new ReserveCommand(inv, repo, new ReservaRequest("SKU-LECHE", 500, "AUTO", "ana"), cadena));
        intentar(invoker, new ReserveCommand(inv, repo, new ReservaRequest("SKU-YOGUR", 10, "AUTO", "ana"), cadena));
        intentar(invoker, new ReserveCommand(inv, repo, new ReservaRequest("SKU-LECHE", 10, "L-103", "ana"), cadena));

        System.out.println("\n=== 2. Idempotencia: reintentar el mismo comando ===");
        System.out.println(invoker.ejecutar(ok) ? "se ejecuto de nuevo" : "DUPLICADO: no se repite");

        System.out.println("\n=== 3. Liberar: solo el dueno puede ===");
        UUID reservaId = UUID.fromString(ok.id());
        intentar(invoker, new ReleaseCommand(inv, repo, reservaId, "mallory"));
        intentar(invoker, new ReleaseCommand(inv, repo, reservaId, "ana"));
        System.out.println("Stock de leche tras liberar: " + inv.stockTotal("SKU-LECHE"));

        System.out.println("\n=== 4. Strategy: misma reserva, distinta politica ===");
        for (PickingPolicy p : new PickingPolicy[]{new FefoPolicy(), new FifoPolicy(), new LifoPolicy()}) {
            picking.setPoliticaGeneral(p);
            ReserveCommand cmd = new ReserveCommand(inv, repo, new ReservaRequest("SKU-LECHE", 40, "AUTO", "ana"), cadena);
            cmd.execute();
            System.out.println(p.nombre() + " -> " + cmd.descripcion());
            cmd.undo();
        }

        System.out.println("\n=== Ledger de auditoria ===");
        invoker.getLedger().forEach(System.out::println);
    }

    private static void intentar(CommandInvoker invoker, Command cmd) {
        try {
            invoker.ejecutar(cmd);
            System.out.println("OK: " + cmd.descripcion());
        } catch (RuntimeException e) {
            String quien = e instanceof ReservaInvalidaException r && r.getEslabon() != null ? r.getEslabon() + ": " : "";
            System.out.println("RECHAZADA (" + quien + e.getMessage() + ")");
        }
    }
}
