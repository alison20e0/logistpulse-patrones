package state;

public class Demo {
    public static void main(String[] args) {
        System.out.println("=== Camino normal ===");
        Lote l1 = new Lote("L-001");
        l1.almacenar(); l1.reservar(); l1.pickear(); l1.enviar();

        System.out.println("\n=== Desvio por cuarentena ===");
        Lote l2 = new Lote("L-002");
        l2.almacenar(); l2.enviarACuarentena(); l2.liberarDeCuarentena();

        System.out.println("\n=== Transiciones invalidas ===");
        Lote l3 = new Lote("L-003");
        intentar("reservar un lote RECIBIDO", l3::reservar);
        l3.almacenar(); l3.enviarACuarentena();
        intentar("reservar un lote en CUARENTENA", l3::reservar);
        intentar("enviar un lote ENVIADO", () -> { l1.enviar(); });
    }

    private static void intentar(String que, Runnable accion) {
        try { accion.run(); System.out.println("  (ok) " + que); }
        catch (TransicionInvalidaException e) { System.out.println("  RECHAZADO al " + que + ": " + e.getMessage()); }
    }
}
