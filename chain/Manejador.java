package chain;

/** CHAIN OF RESPONSIBILITY: interfaz comun. Cada manejador valida y reenvia al siguiente. */
public abstract class Manejador {
    private Manejador siguiente;

    public Manejador setSiguiente(Manejador siguiente) {
        this.siguiente = siguiente;
        return siguiente; // permite encadenar: a.setSiguiente(b).setSiguiente(c)
    }

    public void manejar(Solicitud s, Bodega b) {
        validar(s, b); // si falla lanza excepcion y la cadena se corta
        if (siguiente != null) siguiente.manejar(s, b);
    }

    protected abstract void validar(Solicitud s, Bodega b);

    protected void rechazar(String motivo) {
        throw new SolicitudRechazadaException(getClass().getSimpleName(), motivo);
    }
}
