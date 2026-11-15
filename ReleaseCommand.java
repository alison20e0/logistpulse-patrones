package logistpulse.command;

import java.util.UUID;
import logistpulse.chain.ReservaInvalidaException;
import logistpulse.model.*;

/** Comando concreto: liberar una reserva por UUID. Solo su dueno puede liberarla. */
public class ReleaseCommand implements Command {
    private final UUID id = UUID.randomUUID();
    private final Inventario inventario;
    private final ReservaRepositorio repositorio;
    private final UUID reservaId;
    private final String solicitante;
    private Reserva liberada;

    public ReleaseCommand(Inventario inventario, ReservaRepositorio repositorio, UUID reservaId, String solicitante) {
        this.inventario = inventario;
        this.repositorio = repositorio;
        this.reservaId = reservaId;
        this.solicitante = solicitante;
    }

    public String id() { return id.toString(); }

    public void execute() {
        Reserva r = repositorio.buscar(reservaId)
                .orElseThrow(() -> new ReservaInvalidaException("ReleaseCommand", "No existe la reserva " + reservaId));
        if (!r.isActiva())
            throw new ReservaInvalidaException("ReleaseCommand", "La reserva ya fue liberada");
        if (!r.getSolicitante().equalsIgnoreCase(solicitante))
            throw new ReservaInvalidaException("ReleaseCommand",
                    "Solo el solicitante original (" + r.getSolicitante() + ") puede liberar esta reserva");
        inventario.liberar(r.getAsignaciones());
        r.setActiva(false);
        liberada = r;
    }

    public void undo() {                                   // el inverso de liberar es volver a reservar
        if (liberada == null) return;
        inventario.aplicar(liberada.getAsignaciones());
        liberada.setActiva(true);
    }

    public String descripcion() { return "RELEASE reserva " + reservaId + " por " + solicitante; }
}
