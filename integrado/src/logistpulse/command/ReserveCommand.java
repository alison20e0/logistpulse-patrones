package logistpulse.command;

import java.util.List;
import logistpulse.chain.ValidationHandler;
import logistpulse.model.Asignacion;
import logistpulse.model.Inventario;
import logistpulse.model.ReservaRequest;

/** Comando concreto: reservar stock. Pasa primero por la cadena de validación. */
public class ReserveCommand implements Command {
    private final Inventario inventario;
    private final ReservaRequest request;
    private final ValidationHandler cadena;
    private List<Asignacion> asignaciones = List.of();

    public ReserveCommand(Inventario inventario, ReservaRequest request, ValidationHandler cadena) {
        this.inventario = inventario;
        this.request = request;
        this.cadena = cadena;
    }

    public void execute() {
        cadena.handle(request, inventario);                                    // Chain of Responsibility
        asignaciones = inventario.reservar(request.sku(), request.cantidad()); // usa Strategy
    }

    public void undo() {                                                       // se compensa con su inverso
        inventario.liberar(asignaciones);
        asignaciones = List.of();
    }

    public String descripcion() {
        return "RESERVE " + request.sku() + " x" + request.cantidad() + " -> " + asignaciones;
    }
}
