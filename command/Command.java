package command;

/** COMMAND: cada petición es un objeto con execute() y su inverso undo(). */
public interface Command {
    String id();            // para la idempotencia (reintentos)
    void execute();
    void undo();
    String descripcion();
}
