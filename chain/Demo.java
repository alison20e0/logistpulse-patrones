package chain;

import java.time.LocalDate;

public class Demo {
    public static void main(String[] args) {
        LocalDate hoy = LocalDate.now();
        Bodega bodega = new Bodega(100);
        bodega.agregar(new Lote("L-1", "SKU-LECHE", 60, hoy.plusDays(20), false));
        bodega.agregar(new Lote("L-2", "SKU-LECHE", 50, hoy.minusDays(2), false)); // vencido
        bodega.agregar(new Lote("L-3", "SKU-YOGUR", 40, hoy.plusDays(30), true));  // cuarentena

        // SKU -> stock -> lote -> capacidad
        Manejador cadena = new SkuHandler();
        cadena.setSiguiente(new StockHandler())
              .setSiguiente(new LoteHandler())
              .setSiguiente(new CapacidadHandler());

        Solicitud[] pruebas = {
            new Solicitud("SKU-LECHE", 40),   // valida
            new Solicitud("SKU-XXX", 5),      // falla en SkuHandler
            new Solicitud("SKU-LECHE", 500),  // falla en StockHandler
            new Solicitud("SKU-LECHE", 80),   // falla en LoteHandler (hay 110 pero 50 vencidos)
            new Solicitud("SKU-YOGUR", 10),   // falla en LoteHandler (cuarentena)
        };
        for (Solicitud s : pruebas) {
            try {
                cadena.manejar(s, bodega);
                System.out.println("APROBADA  " + s);
            } catch (SolicitudRechazadaException e) {
                System.out.println("RECHAZADA " + s + " por " + e.eslabon() + ": " + e.getMessage());
            }
        }
    }
}
