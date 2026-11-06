package biblioteca;

public class Confirmada implements EstadoReserva {
    @Override
    public String nombre() {
        return "Confirmada";
    }

    @Override
    public void confirmar(Reserva reserva) {
        throw new IllegalStateException("La reserva ya está confirmada.");
    }

    @Override
    public void cancelar(Reserva reserva) {
        reserva.cambiarEstado(new Cancelada());
    }

    @Override
    public void finalizar(Reserva reserva) {
        reserva.cambiarEstado(new Finalizada());
    }
}
