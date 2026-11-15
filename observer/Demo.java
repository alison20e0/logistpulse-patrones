package observer;

public class Demo {
    public static void main(String[] args) {
        Inventario inv = new Inventario();
        LedgerListener ledger = new LedgerListener();
        PulseFleetListener fleet = new PulseFleetListener();

        inv.suscribir(new ReposicionListener(20));
        inv.suscribir(fleet);
        inv.suscribir(ledger);

        System.out.println("--- Recepcion de 100 u. ---");
        inv.ajustar("SKU-LECHE", 100, "recepcion");
        System.out.println("--- Reserva de 85 u. (cae bajo el punto de reorden) ---");
        inv.ajustar("SKU-LECHE", -85, "reserva");

        System.out.println("--- PULSEFLEET se da de baja en ejecucion ---");
        inv.darDeBaja(fleet);
        inv.ajustar("SKU-LECHE", 50, "recepcion");

        System.out.println("\n--- Ledger ---");
        ledger.registros().forEach(System.out::println);
    }
}
