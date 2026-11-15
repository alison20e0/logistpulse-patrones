package state;

public class Pickeado extends EstadoBase {
    public String nombre() { return "PICKEADO"; }
    @Override public void enviar(Lote l) { l.setEstado(new Enviado()); }
}
