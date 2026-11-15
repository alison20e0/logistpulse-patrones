package state;

/** STATE: interfaz comun. Cada estado decide que acciones permite. */
public interface EstadoLote {
    String nombre();
    void almacenar(Lote l);
    void reservar(Lote l);
    void liberarReserva(Lote l);
    void pickear(Lote l);
    void enviar(Lote l);
    void enviarACuarentena(Lote l);
    void liberarDeCuarentena(Lote l);
}
