package logistpulse.chain;

/** El eslabón que falla rechaza la solicitud con un motivo claro. */
public class ReservaInvalidaException extends RuntimeException {
    public ReservaInvalidaException(String motivo) { super(motivo); }
}
