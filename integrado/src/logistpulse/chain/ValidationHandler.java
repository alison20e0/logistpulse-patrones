package logistpulse.chain;

import logistpulse.model.Inventario;
import logistpulse.model.ReservaRequest;

/** CHAIN OF RESPONSIBILITY: interfaz común (clase base) de los manejadores. */
public abstract class ValidationHandler {
    private ValidationHandler siguiente;

    /** Enlaza el siguiente eslabón y lo devuelve para encadenar llamadas. */
    public ValidationHandler setSiguiente(ValidationHandler siguiente) {
        this.siguiente = siguiente;
        return siguiente;
    }

    public void handle(ReservaRequest req, Inventario inv) {
        validar(req, inv); // si falla, lanza excepción y la cadena se corta
        if (siguiente != null) siguiente.handle(req, inv);
    }

    protected abstract void validar(ReservaRequest req, Inventario inv);
}
