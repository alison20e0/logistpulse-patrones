package command;

public class Demo {
    public static void main(String[] args) {
        Inventory inv = new Inventory();
        inv.recibir("SKU-LECHE", 100);
        CommandInvoker invoker = new CommandInvoker();

        invoker.ejecutar(new ReserveCommand("C-1", inv, "SKU-LECHE", 30));
        invoker.ejecutar(new ReserveCommand("C-1", inv, "SKU-LECHE", 30)); // reintento: se ignora
        invoker.ejecutar(new AdjustCommand("C-2", inv, "SKU-LECHE", -5));  // merma
        invoker.ejecutar(new ReserveCommand("C-3", inv, "SKU-LECHE", 500)); // rechazado
        invoker.ejecutar(new ReleaseCommand("C-4", inv, "SKU-LECHE", 10));
        System.out.println("Stock tras operaciones: " + inv.stock("SKU-LECHE"));

        invoker.deshacerUltimo(); // deshace C-4
        invoker.deshacerUltimo(); // deshace C-2
        System.out.println("Stock tras 2 undo: " + inv.stock("SKU-LECHE"));

        System.out.println("\n--- Ledger ---");
        invoker.ledger().forEach(System.out::println);
    }
}
