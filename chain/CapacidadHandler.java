package chain;

public class CapacidadHandler extends Manejador {
    protected void validar(Solicitud s, Bodega b) {
        if (s.cantidad() > b.capacidadMaxima())
            rechazar("Excede la capacidad maxima por reserva (" + b.capacidadMaxima() + ")");
    }
}
