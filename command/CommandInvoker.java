package command;

import java.util.*;

/** INVOKER: solo dispara comandos. Guarda historial (undo), ledger (auditoria) e ids (idempotencia). */
public class CommandInvoker {
    private final Deque<Command> historial = new ArrayDeque<>();
    private final List<String> ledger = new ArrayList<>();
    private final Set<String> idsProcesados = new HashSet<>();

    public void ejecutar(Command cmd) {
        if (!idsProcesados.add(cmd.id())) {          // idempotencia: mismo id = no se repite
            ledger.add("DUPLICADO | " + cmd.id() + " | " + cmd.descripcion());
            return;
        }
        try {
            cmd.execute();
            historial.push(cmd);
            ledger.add("OK        | " + cmd.id() + " | " + cmd.descripcion());
        } catch (RuntimeException e) {
            idsProcesados.remove(cmd.id());          // fallo: se puede reintentar con el mismo id
            ledger.add("RECHAZO   | " + cmd.id() + " | " + cmd.descripcion() + " | " + e.getMessage());
        }
    }

    public void deshacerUltimo() {
        if (historial.isEmpty()) return;
        Command cmd = historial.pop();
        cmd.undo();
        ledger.add("UNDO      | " + cmd.id() + " | " + cmd.descripcion());
    }

    public List<String> ledger() { return ledger; }
}
