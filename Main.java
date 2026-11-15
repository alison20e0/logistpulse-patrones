package logistpulse;

import java.util.Collection;
import java.util.Scanner;
import java.util.UUID;
import logistpulse.chain.ReservaInvalidaException;
import logistpulse.chain.ValidationHandler;
import logistpulse.command.*;
import logistpulse.model.*;
import logistpulse.strategy.*;

/** Menu interactivo por consola del Smart Inventory de LOGISTPULSE. */
public class Main {
    /** Se lanza cuando ya no hay mas entrada (Ctrl+D / fin de archivo). */
    private static class EntradaTerminada extends RuntimeException {}

    private final Scanner sc = new Scanner(System.in);
    private final Picking picking = new Picking(new FefoPolicy());
    private final Reposicion reposicion = new Reposicion(new ReorderPointPolicy(20, 100));
    private final Inventario inventario = Datos.crearInventario(picking);
    private final ValidationHandler cadena = Datos.crearCadena();
    private final ReservaRepositorio reservas = new ReservaRepositorio();
    private final CommandInvoker invoker = new CommandInvoker();
    private Command ultimoComando;

    public static void main(String[] args) {
        new Main().ejecutar();
    }

    private void ejecutar() {
        System.out.println("==========================================");
        System.out.println(" LOGISTPULSE · Smart Inventory");
        System.out.println(" Command + Chain of Responsibility + Strategy");
        System.out.println("==========================================");
        try {
            boolean seguir = true;
            while (seguir) {
                mostrarMenu();
                String opcion = leer("Opcion");
                try {
                    switch (opcion.trim()) {
                        case "1" -> consultarProductos();
                        case "2" -> reservarStock();
                        case "3" -> liberarReserva();
                        case "4" -> consultarReservas();
                        case "5" -> deshacerUltimo();
                        case "6" -> reintentarUltimo();
                        case "7" -> verLedger();
                        case "8" -> cambiarPoliticaPicking();
                        case "9" -> menuReposicion();
                        case "10" -> alternarCuarentena();
                        case "0" -> seguir = false;
                        default -> System.out.println("Opcion no valida.");
                    }
                } catch (ReservaInvalidaException e) {
                    String donde = e.getEslabon() != null ? " por " + e.getEslabon() : "";
                    System.out.println("RECHAZADA" + donde + ": " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("Entrada invalida: se esperaba un numero.");
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            System.out.println("Hasta luego.");
        } catch (EntradaTerminada e) {
            System.out.println("\nFin de la entrada. Hasta luego.");
        }
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("--------- MENU DEL SISTEMA ---------");
        System.out.println(" 1  Consultar productos y lotes");
        System.out.println(" 2  Reservar stock");
        System.out.println(" 3  Liberar una reserva (UUID)");
        System.out.println(" 4  Consultar reservas y lotes asignados");
        System.out.println(" 5  Deshacer la ultima operacion");
        System.out.println(" 6  Reintentar el ultimo comando (idempotencia)");
        System.out.println(" 7  Ver ledger de auditoria");
        System.out.println(" 8  Cambiar politica de picking (Strategy)");
        System.out.println(" 9  Recomendacion de reposicion (Strategy)");
        System.out.println("10  Poner o sacar un lote de cuarentena");
        System.out.println(" 0  Salir");
        System.out.println("Politica de picking activa: " + picking.getGeneral().nombre());
    }

    // ---------------- 1 ----------------
    private void consultarProductos() {
        for (Producto p : inventario.productos()) {
            System.out.printf("%n%s · %s · stock total %d · utilizable %d%n",
                    p.sku(), p.nombre(), inventario.stockTotal(p.sku()), inventario.stockUtilizable(p.sku()));
            System.out.printf("  %-7s %-8s %-12s %-12s %s%n", "Lote", "Cant.", "Recepcion", "Vence", "Estado");
            for (Lote l : inventario.lotes(p.sku())) {
                System.out.printf("  %-7s %-8d %-12s %-12s %s%n",
                        l.getId(), l.getCantidad(), l.getFechaIngreso(), l.getFechaVencimiento(), l.estado());
            }
        }
    }

    // ---------------- 2 ----------------
    private void reservarStock() {
        String sku = leer("SKU (ej. SKU-LECHE)").trim().toUpperCase();
        String lote = leer("Lote (AUTO o ID, ej. L-102) [AUTO]").trim();
        int cantidad = Integer.parseInt(leer("Cantidad").trim());
        String solicitante = leer("Solicitante").trim();

        ReservaRequest req = new ReservaRequest(sku, cantidad, lote.isEmpty() ? "AUTO" : lote, solicitante);
        ReserveCommand cmd = new ReserveCommand(inventario, reservas, req, cadena);
        ultimoComando = cmd;
        invoker.ejecutar(cmd);
        String politica = req.esAuto() ? " (politica " + picking.politicaPara(sku).nombre() + ")" : " (lote manual)";
        System.out.println("RESERVA APROBADA");
        System.out.println("  UUID: " + cmd.id());
        System.out.println("  Lotes asignados: " + reservas.buscar(UUID.fromString(cmd.id())).orElseThrow().getAsignaciones() + politica);
    }

    // ---------------- 3 ----------------
    private void liberarReserva() {
        UUID id;
        try {
            id = UUID.fromString(leer("UUID de la reserva").trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("UUID invalido. Copialo desde la opcion 4.");
        }
        String solicitante = leer("Solicitante").trim();
        ReleaseCommand cmd = new ReleaseCommand(inventario, reservas, id, solicitante);
        ultimoComando = cmd;
        invoker.ejecutar(cmd);
        System.out.println("RESERVA LIBERADA: " + id);
    }

    // ---------------- 4 ----------------
    private void consultarReservas() {
        Collection<Reserva> todas = reservas.todas();
        if (todas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        for (Reserva r : todas) {
            System.out.printf("%s | %s x%d | %s | %s | lotes: %s%n", r.getId(), r.getRequest().sku(),
                    r.getRequest().cantidad(), r.getSolicitante(), r.isActiva() ? "ACTIVA" : "LIBERADA", r.getAsignaciones());
        }
    }

    // ---------------- 5 ----------------
    private void deshacerUltimo() {
        String desc = invoker.deshacerUltimo();
        System.out.println(desc == null ? "No hay operaciones para deshacer." : "Deshecho: " + desc);
    }

    // ---------------- 6 ----------------
    private void reintentarUltimo() {
        if (ultimoComando == null) {
            System.out.println("Todavia no hay ningun comando para reintentar.");
            return;
        }
        boolean ejecutado = invoker.ejecutar(ultimoComando);
        System.out.println(ejecutado
                ? "El comando se ejecuto de nuevo (el anterior habia sido rechazado o deshecho)."
                : "DUPLICADO: ese comando ya se proceso, no se repite (idempotencia).");
    }

    // ---------------- 7 ----------------
    private void verLedger() {
        if (invoker.getLedger().isEmpty()) System.out.println("El ledger esta vacio.");
        else invoker.getLedger().forEach(System.out::println);
    }

    // ---------------- 8 ----------------
    private void cambiarPoliticaPicking() {
        String alcance = leer("Aplicar a: (G)eneral o (S)KU concreto").trim().toUpperCase();
        PickingPolicy p = elegirPicking(leer("Politica (FEFO / FIFO / LIFO)"));
        if (alcance.startsWith("S")) {
            String sku = leer("SKU").trim().toUpperCase();
            if (!inventario.existeSku(sku)) throw new IllegalArgumentException("SKU inexistente: " + sku);
            picking.setPoliticaSku(sku, p);
            System.out.println("Politica de " + sku + ": " + p.nombre());
        } else {
            picking.setPoliticaGeneral(p);
            System.out.println("Politica general: " + p.nombre());
        }
    }

    private PickingPolicy elegirPicking(String nombre) {
        return switch (nombre.trim().toUpperCase()) {
            case "FEFO" -> new FefoPolicy();
            case "FIFO" -> new FifoPolicy();
            case "LIFO" -> new LifoPolicy();
            default -> throw new IllegalArgumentException("Politica desconocida: " + nombre);
        };
    }

    // ---------------- 9 ----------------
    private void menuReposicion() {
        System.out.println("  a) Ver recomendaciones   b) Cambiar politica");
        String op = leer("Opcion").trim().toLowerCase();
        if (op.equals("b")) {
            String alcance = leer("Aplicar a: (G)eneral o (S)KU concreto").trim().toUpperCase();
            ReplenishmentPolicy p = elegirReposicion(leer("Politica (REORDEN / MINMAX / EOQ)"));
            if (alcance.startsWith("S")) {
                String sku = leer("SKU").trim().toUpperCase();
                if (!inventario.existeSku(sku)) throw new IllegalArgumentException("SKU inexistente: " + sku);
                reposicion.setPoliticaSku(sku, p);
            } else {
                reposicion.setPoliticaGeneral(p);
            }
            System.out.println("Politica de reposicion actualizada: " + p.nombre());
        }
        System.out.println("\nRecomendaciones (son sugerencias, no compras reales):");
        for (Producto p : inventario.productos()) {
            int stock = inventario.stockUtilizable(p.sku());
            int pedir = reposicion.recomendar(p.sku(), stock, p.demandaAnual());
            System.out.printf("  %-10s stock utilizable %-4d politica %-30s -> %s%n", p.sku(), stock,
                    reposicion.politicaPara(p.sku()).nombre(), pedir > 0 ? "pedir " + pedir + " u." : "no hace falta pedir");
        }
    }

    private ReplenishmentPolicy elegirReposicion(String nombre) {
        return switch (nombre.trim().toUpperCase()) {
            case "REORDEN" -> new ReorderPointPolicy(20, 100);
            case "MINMAX" -> new MinMaxPolicy(20, 200);
            case "EOQ" -> new EoqPolicy(50, 2);
            default -> throw new IllegalArgumentException("Politica desconocida: " + nombre);
        };
    }

    // ---------------- 10 ----------------
    private void alternarCuarentena() {
        String sku = leer("SKU").trim().toUpperCase();
        String loteId = leer("ID del lote").trim();
        Lote lote = inventario.buscarLote(sku, loteId)
                .orElseThrow(() -> new IllegalArgumentException("No existe el lote " + loteId + " en " + sku));
        lote.setEnCuarentena(!lote.isEnCuarentena());
        System.out.println("Lote " + lote.getId() + " ahora esta: " + lote.estado());
    }

    // ---------------- entrada ----------------
    private String leer(String mensaje) {
        System.out.print(mensaje + ": ");
        if (!sc.hasNextLine()) throw new EntradaTerminada();
        return sc.nextLine();
    }
}
