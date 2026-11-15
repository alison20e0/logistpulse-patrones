package state;

/** Una transicion invalida lanza excepcion. */
public class TransicionInvalidaException extends RuntimeException {
    public TransicionInvalidaException(String msg) { super(msg); }
}
