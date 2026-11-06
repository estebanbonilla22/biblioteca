package biblioteca;

import java.util.ArrayList;
import java.util.List;

public class RepositorioReservasMemoria implements RepositorioReservas {
    private final List<Reserva> reservas = new ArrayList<>();

    @Override
    public void guardar(Reserva reserva) {
        reservas.add(reserva);
    }

    public List<Reserva> obtenerTodas() {
        return List.copyOf(reservas);
    }
}
