package logistpulse.command;

/** COMMAND: cada peticion es un objeto con execute() y su inverso undo(). */
public interface Command {
    String id();            // para la idempotencia
    void execute();
    void undo();
    String descripcion();
}
