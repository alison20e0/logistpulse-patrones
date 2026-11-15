package logistpulse.model;

import java.util.List;
import java.util.UUID;

/** Una reserva aprobada: queda registrada con su UUID, su dueno y los lotes asignados. */
public class Reserva {
    private final UUID id;
    private final ReservaRequest request;
    private final List<Asignacion> asignaciones;
    private boolean activa = true;

    public Reserva(UUID id, ReservaRequest request, List<Asignacion> asignaciones) {
        this.id = id;
        this.request = request;
        this.asignaciones = asignaciones;
    }

    public UUID getId() { return id; }
    public ReservaRequest getRequest() { return request; }
    public List<Asignacion> getAsignaciones() { return asignaciones; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
    public String getSolicitante() { return request.solicitante(); }
}
