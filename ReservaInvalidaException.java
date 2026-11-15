package logistpulse.chain;

/** El eslabon que falla rechaza la solicitud con un motivo claro. */
public class ReservaInvalidaException extends RuntimeException {
    private final String eslabon;

    public ReservaInvalidaException(String eslabon, String motivo) {
        super(motivo);
        this.eslabon = eslabon;
    }
    public ReservaInvalidaException(String motivo) { this(null, motivo); }

    public String getEslabon() { return eslabon; }
}
