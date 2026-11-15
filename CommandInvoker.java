package logistpulse.command;

import java.time.LocalTime;
import java.util.*;

/** INVOKER: solo dispara comandos. Guarda ledger (auditoria), historial (undo) e ids (idempotencia). */
public class CommandInvoker {
    private final Deque<Command> historial = new ArrayDeque<>();
    private final List<String> ledger = new ArrayList<>();
    private final Set<String> idsProcesados = new HashSet<>();

    private String hora() { return LocalTime.now().withNano(0).toString(); }

    /** Devuelve false si el comando ya se habia procesado (mismo id): no se repite. */
    public boolean ejecutar(Command cmd) {
        if (!idsProcesados.add(cmd.id())) {
            ledger.add(hora() + " DUPLICADO | " + cmd.descripcion());
            return false;
        }
        try {
            cmd.execute();
            historial.push(cmd);
            ledger.add(hora() + " OK        | " + cmd.descripcion());
            return true;
        } catch (RuntimeException e) {
            idsProcesados.remove(cmd.id()); // un comando rechazado se puede reintentar
            ledger.add(hora() + " RECHAZO   | " + cmd.descripcion() + " | motivo: " + e.getMessage());
            throw e;
        }
    }

    /** Deshace el ultimo comando ejecutado. Devuelve su descripcion o null si no hay nada que deshacer. */
    public String deshacerUltimo() {
        if (historial.isEmpty()) return null;
        Command cmd = historial.pop();
        String desc = cmd.descripcion();
        cmd.undo();
        idsProcesados.remove(cmd.id());
        ledger.add(hora() + " UNDO      | " + desc);
        return desc;
    }

    public List<String> getLedger() { return ledger; }
}
