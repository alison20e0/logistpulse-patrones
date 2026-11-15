package chain;

/** El eslabon que falla rechaza con un motivo claro. */
public class SolicitudRechazadaException extends RuntimeException {
    private final String eslabon;
    public SolicitudRechazadaException(String eslabon, String motivo) {
        super(motivo);
        this.eslabon = eslabon;
    }
    public String eslabon() { return eslabon; }
}
