package logistpulse.chain;

import logistpulse.model.Inventario;
import logistpulse.model.ReservaRequest;

public class CapacidadHandler extends ValidationHandler {
    protected void validar(ReservaRequest req, Inventario inv) {
        if (req.cantidad() > inv.capacidadMaximaPorReserva())
            throw new ReservaInvalidaException(
                "Excede la capacidad máxima por reserva (" + inv.capacidadMaximaPorReserva() + ")");
    }
}
