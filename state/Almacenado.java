package state;

public class Almacenado extends EstadoBase {
    public String nombre() { return "ALMACENADO"; }
    @Override public void reservar(Lote l) { l.setEstado(new Reservado()); }
    @Override public void enviarACuarentena(Lote l) { l.setEstado(new Cuarentena()); }
}
