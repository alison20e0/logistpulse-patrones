package state;

/** CONTEXTO del State: guarda el estado activo, le delega el trabajo y cambia sustituyendolo. */
public class Lote {
    private final String id;
    private EstadoLote estado = new Recibido();

    public Lote(String id) { this.id = id; }

    void setEstado(EstadoLote nuevo) {
        System.out.println("  [" + id + "] " + estado.nombre() + " -> " + nuevo.nombre());
        this.estado = nuevo;
    }
    public String estado() { return estado.nombre(); }

    public void almacenar() { estado.almacenar(this); }
    public void reservar() { estado.reservar(this); }
    public void liberarReserva() { estado.liberarReserva(this); }
    public void pickear() { estado.pickear(this); }
    public void enviar() { estado.enviar(this); }
    public void enviarACuarentena() { estado.enviarACuarentena(this); }
    public void liberarDeCuarentena() { estado.liberarDeCuarentena(this); }
}
