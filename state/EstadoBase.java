package state;

/** Por defecto toda accion es invalida; cada estado concreto solo sobrescribe lo que permite. */
public abstract class EstadoBase implements EstadoLote {
    private void invalida(String accion) {
        throw new TransicionInvalidaException("No se puede " + accion + " un lote en estado " + nombre());
    }
    public void almacenar(Lote l) { invalida("almacenar"); }
    public void reservar(Lote l) { invalida("reservar"); }
    public void liberarReserva(Lote l) { invalida("liberar la reserva de"); }
    public void pickear(Lote l) { invalida("pickear"); }
    public void enviar(Lote l) { invalida("enviar"); }
    public void enviarACuarentena(Lote l) { invalida("enviar a cuarentena"); }
    public void liberarDeCuarentena(Lote l) { invalida("sacar de cuarentena"); }
}
