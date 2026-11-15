package logistpulse.model;

/** La solicitud que recorre la cadena. loteId = "AUTO" (o vacio) deja que Strategy elija el lote. */
public record ReservaRequest(String sku, int cantidad, String loteId, String solicitante) {
    public boolean esAuto() { return loteId == null || loteId.isBlank() || loteId.equalsIgnoreCase("AUTO"); }
}
