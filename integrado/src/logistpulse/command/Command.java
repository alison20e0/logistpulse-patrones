package logistpulse.command;

/** COMMAND: cada petición es un objeto con execute() y su inverso undo(). */
public interface Command {
    void execute();
    void undo();
    String descripcion();
}
