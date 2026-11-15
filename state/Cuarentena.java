package state;

/** Desvio: desde aqui solo se puede volver a Almacenado si el lote se libera. */
public class Cuarentena extends EstadoBase {
    public String nombre() { return "CUARENTENA"; }
    @Override public void liberarDeCuarentena(Lote l) { l.setEstado(new Almacenado()); }
}
