package chain;

import java.time.LocalDate;

public record Lote(String id, String sku, int cantidad, LocalDate vence, boolean enCuarentena) {
    public boolean utilizable() { return !enCuarentena && !vence.isBefore(LocalDate.now()); }
}
