package state;

public class Reservado extends EstadoBase {
    public String nombre() { return "RESERVADO"; }
    @Override public void pickear(Lote l) { l.setEstado(new Pickeado()); }
    @Override public void liberarReserva(Lote l) { l.setEstado(new Almacenado()); }
}
