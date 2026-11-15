package logistpulse.model;

import java.util.*;
import logistpulse.strategy.Picking;

/** RECEIVER del patron Command: aqui vive la logica de negocio real. */
public class Inventario {
    private final Map<String, Producto> productos = new LinkedHashMap<>();
    private final Map<String, List<Lote>> lotesPorSku = new HashMap<>();
    private final int capacidadMaximaPorReserva;
    private final Picking picking;

    public Inventario(int capacidadMaximaPorReserva, Picking picking) {
        this.capacidadMaximaPorReserva = capacidadMaximaPorReserva;
        this.picking = picking;
    }

    public void agregarProducto(Producto p) {
        productos.put(p.sku(), p);
        lotesPorSku.putIfAbsent(p.sku(), new ArrayList<>());
    }

    public void agregarLote(Lote lote) {
        lotesPorSku.computeIfAbsent(lote.getSku(), k -> new ArrayList<>()).add(lote);
    }

    public Collection<Producto> productos() { return productos.values(); }
    public Optional<Producto> producto(String sku) { return Optional.ofNullable(productos.get(sku)); }
    public boolean existeSku(String sku) { return sku != null && lotesPorSku.containsKey(sku); }
    public int capacidadMaximaPorReserva() { return capacidadMaximaPorReserva; }
    public Picking getPicking() { return picking; }

    public List<Lote> lotes(String sku) { return lotesPorSku.getOrDefault(sku, List.of()); }

    public Optional<Lote> buscarLote(String sku, String loteId) {
        return lotes(sku).stream().filter(l -> l.getId().equalsIgnoreCase(loteId)).findFirst();
    }

    public int stockTotal(String sku) {
        return lotes(sku).stream().mapToInt(Lote::getCantidad).sum();
    }

    public List<Lote> lotesUtilizables(String sku) {
        return lotes(sku).stream().filter(Lote::esUtilizable).toList();
    }

    public int stockUtilizable(String sku) {
        return lotesUtilizables(sku).stream().mapToInt(Lote::getCantidad).sum();
    }

    /** Reserva: en modo AUTO elige los lotes la politica de picking (Strategy); si no, usa el lote indicado. */
    public List<Asignacion> reservar(ReservaRequest req) {
        List<Asignacion> asignaciones;
        if (req.esAuto()) {
            asignaciones = picking.seleccionar(req.sku(), lotesUtilizables(req.sku()), req.cantidad());
        } else {
            Lote lote = buscarLote(req.sku(), req.loteId()).orElseThrow();
            asignaciones = List.of(new Asignacion(lote, req.cantidad()));
        }
        aplicar(asignaciones);
        return asignaciones;
    }

    /** Descuenta del stock las unidades de cada asignacion. */
    public void aplicar(List<Asignacion> asignaciones) {
        asignaciones.forEach(a -> a.lote().quitar(a.cantidad()));
    }

    /** Operacion inversa: devuelve las unidades a sus lotes. */
    public void liberar(List<Asignacion> asignaciones) {
        asignaciones.forEach(a -> a.lote().agregar(a.cantidad()));
    }
}
