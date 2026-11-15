package logistpulse.strategy;

import java.util.*;
import logistpulse.model.Asignacion;
import logistpulse.model.Lote;

/** STRATEGY: el CONTEXTO. Politica general y, si se quiere, una distinta por SKU. */
public class Picking {
    private PickingPolicy general;
    private final Map<String, PickingPolicy> porSku = new HashMap<>();

    public Picking(PickingPolicy general) { this.general = general; }

    public void setPoliticaGeneral(PickingPolicy p) { this.general = p; porSku.clear(); }
    public void setPoliticaSku(String sku, PickingPolicy p) { porSku.put(sku, p); }
    public PickingPolicy getGeneral() { return general; }
    public PickingPolicy politicaPara(String sku) { return porSku.getOrDefault(sku, general); }

    public List<Asignacion> seleccionar(String sku, List<Lote> lotesUtilizables, int cantidad) {
        List<Asignacion> resultado = new ArrayList<>();
        int pendiente = cantidad;
        for (Lote lote : politicaPara(sku).ordenar(lotesUtilizables)) {
            if (pendiente == 0) break;
            int tomar = Math.min(lote.getCantidad(), pendiente);
            if (tomar > 0) {
                resultado.add(new Asignacion(lote, tomar));
                pendiente -= tomar;
            }
        }
        return resultado;
    }
}
