package logistpulse.model;

/** La solicitud que recorre la cadena de validación. */
public record ReservaRequest(String sku, int cantidad) {}
