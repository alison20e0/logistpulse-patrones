package state;

public class Recibido extends EstadoBase {
    public String nombre() { return "RECIBIDO"; }
    @Override public void almacenar(Lote l) { l.setEstado(new Almacenado()); }
    @Override public void enviarACuarentena(Lote l) { l.setEstado(new Cuarentena()); }
}
