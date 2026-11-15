# LOGISTPULSE · Smart Inventory — Patrones de comportamiento (Java)

Cada patron esta en su propia carpeta y se puede compilar y ejecutar por separado.
El ejemplo comun es una reserva de stock de LOGISTPULSE (diapositivas del curso).

| Carpeta | Patron | Que muestra |
|---|---|---|
| `command/` | Command | `ReserveCommand`, `ReleaseCommand`, `AdjustCommand`; el `CommandInvoker` guarda historial (undo), ledger e idempotencia por id |
| `chain/` | Chain of Responsibility | SKU → stock → lote (vencido/cuarentena) → capacidad; el eslabon que falla rechaza con un motivo |
| `strategy/` | Strategy | `PickingPolicy` (FEFO, FIFO, LIFO) y `ReplenishmentPolicy` (punto de reorden, Min-Max, EOQ) intercambiables en ejecucion |
| `state/` | State | El lote pasa por Recibido → Almacenado → Reservado → Pickeado → Enviado, con Cuarentena como desvio; una transicion invalida lanza excepcion |
| `observer/` | Observer | El inventario publica `StockChanged`; Reposicion, PULSEFLEET y el Ledger se suscriben y se dan de baja en ejecucion |
| `integrado/` | Command + Chain + Strategy juntos | Una reserva completa: el comando pasa por la cadena y usa la politica de picking activa |

## Ejecutar (Java 17 o superior)

Desde la raiz del repo. Cambia `command` por `chain`, `strategy`, `state` u `observer`:

```bash
mkdir -p out
javac -encoding UTF-8 -d out command/*.java
java -cp out command.Demo
```

Para la carpeta `integrado`:

```bash
mkdir -p out
javac -encoding UTF-8 -d out $(find integrado/src -name "*.java")
java -cp out logistpulse.Main
```
