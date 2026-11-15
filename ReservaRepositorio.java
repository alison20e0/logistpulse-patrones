package logistpulse.model;

import java.util.*;

public class ReservaRepositorio {
    private final Map<UUID, Reserva> reservas = new LinkedHashMap<>();

    public void agregar(Reserva r) { reservas.put(r.getId(), r); }
    public void quitar(UUID id) { reservas.remove(id); }
    public Optional<Reserva> buscar(UUID id) { return Optional.ofNullable(reservas.get(id)); }
    public Collection<Reserva> todas() { return reservas.values(); }
}
