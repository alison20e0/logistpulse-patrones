package logistpulse.command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/** INVOKER: solo dispara comandos. Guarda el ledger (auditoría) y el historial para deshacer. */
public class CommandInvoker {
    private final Deque<Command> historial = new ArrayDeque<>();
    private final List<String> ledger = new ArrayList<>();

    public void ejecutar(Command cmd) {
        try {
            cmd.execute();
            historial.push(cmd);
            ledger.add("OK      | " + cmd.descripcion());
        } catch (RuntimeException e) {
            ledger.add("RECHAZO | " + cmd.descripcion() + " | motivo: " + e.getMessage());
            throw e;
        }
    }

    public void deshacerUltimo() {
        if (historial.isEmpty()) return;
        Command cmd = historial.pop();
        String desc = cmd.descripcion();
        cmd.undo();
        ledger.add("UNDO    | " + desc);
    }

    public List<String> getLedger() { return ledger; }
}
