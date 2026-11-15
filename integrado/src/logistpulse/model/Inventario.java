package logistpulse.model;

import java.util.*;
import logistpulse.strategy.Picking;

/** RECEIVER del patrón Command: aquí vive la lógica de negocio real. */
public class Inventario {
    private final Map<String, List<Lote>> lotesPorSku = new HashMap<>();
    private final int capacidadMaximaPorReserva;
    private final Picking picking;

    public Inventario(int capacidadMaximaPorReserva, Picking picking) {
        this.capacidadMaximaPorReserva = capacidadMaximaPorReserva;
        this.picking = picking;
    }

    public void agregarLote(Lote lote) {
        lotesPorSku.computeIfAbsent(lote.getSku(), k -> new ArrayList<>()).add(lote);
    }

    public boolean existeSku(String sku) { return lotesPorSku.containsKey(sku); }
    public int capacidadMaximaPorReserva() { return capacidadMaximaPorReserva; }
    public Picking getPicking() { return picking; }

    public List<Lote> lotes(String sku) { return lotesPorSku.getOrDefault(sku, List.of()); }

    public int stockTotal(String sku) {
        return lotes(sku).stream().mapToInt(Lote::getCantidad).sum();
    }

    public List<Lote> lotesUtilizables(String sku) {
        return lotes(sku).stream().filter(Lote::esUtilizable).toList();
    }

    public int stockUtilizable(String sku) {
        return lotesUtilizables(sku).stream().mapToInt(Lote::getCantidad).sum();
    }

    /** Reserva usando la política de picking activa (Strategy). */
    public List<Asignacion> reservar(String sku, int cantidad) {
        List<Asignacion> asignaciones = picking.seleccionar(lotesUtilizables(sku), cantidad);
        asignaciones.forEach(a -> a.lote().quitar(a.cantidad()));
        return asignaciones;
    }

    /** Operación inversa: devuelve las unidades a sus lotes. */
    public void liberar(List<Asignacion> asignaciones) {
        asignaciones.forEach(a -> a.lote().agregar(a.cantidad()));
    }
}
