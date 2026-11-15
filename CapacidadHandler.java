package logistpulse.chain;

import logistpulse.model.Inventario;
import logistpulse.model.ReservaRequest;

public class CapacidadHandler extends ValidationHandler {
    protected void validar(ReservaRequest req, Inventario inv) {
        if (req.cantidad() > inv.capacidadMaximaPorReserva())
            rechazar("Excede la capacidad maxima por reserva (" + inv.capacidadMaximaPorReserva() + ")");
    }
}
