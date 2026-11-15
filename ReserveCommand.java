package logistpulse.command;

import java.util.List;
import java.util.UUID;
import logistpulse.chain.ValidationHandler;
import logistpulse.model.*;

/** Comando concreto: reservar stock. Pasa primero por la cadena de validacion. */
public class ReserveCommand implements Command {
    private final UUID id = UUID.randomUUID();
    private final Inventario inventario;
    private final ReservaRepositorio repositorio;
    private final ReservaRequest request;
    private final ValidationHandler cadena;
    private List<Asignacion> asignaciones = List.of();

    public ReserveCommand(Inventario inventario, ReservaRepositorio repositorio,
                          ReservaRequest request, ValidationHandler cadena) {
        this.inventario = inventario;
        this.repositorio = repositorio;
        this.request = request;
        this.cadena = cadena;
    }

    public String id() { return id.toString(); }

    public void execute() {
        cadena.handle(request, inventario);                // Chain of Responsibility
        asignaciones = inventario.reservar(request);       // usa Strategy si es AUTO
        repositorio.agregar(new Reserva(id, request, asignaciones));
    }

    public void undo() {                                   // se compensa con su inverso
        inventario.liberar(asignaciones);
        repositorio.quitar(id);
    }

    public String descripcion() {
        return "RESERVE " + request.sku() + " x" + request.cantidad() + " por " + request.solicitante()
                + (asignaciones.isEmpty() ? "" : " -> " + asignaciones);
    }
}
