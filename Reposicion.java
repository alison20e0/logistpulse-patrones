package logistpulse.strategy;

import java.util.HashMap;
import java.util.Map;

/** STRATEGY: contexto de reposicion. Politica general o por SKU, cambiable en ejecucion. */
public class Reposicion {
    private ReplenishmentPolicy general;
    private final Map<String, ReplenishmentPolicy> porSku = new HashMap<>();

    public Reposicion(ReplenishmentPolicy general) { this.general = general; }

    public void setPoliticaGeneral(ReplenishmentPolicy p) { this.general = p; porSku.clear(); }
    public void setPoliticaSku(String sku, ReplenishmentPolicy p) { porSku.put(sku, p); }
    public ReplenishmentPolicy politicaPara(String sku) { return porSku.getOrDefault(sku, general); }

    public int recomendar(String sku, int stockActual, int demandaAnual) {
        return politicaPara(sku).cantidadAPedir(stockActual, demandaAnual);
    }
}
