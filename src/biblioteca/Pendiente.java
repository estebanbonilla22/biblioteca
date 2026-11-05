package biblioteca;

public class Pendiente implements EstadoReserva {
    @Override
    public String nombre() {
        return "Pendiente";
    }

    @Override
    public void confirmar(Reserva reserva) {
        reserva.cambiarEstado(new Confirmada());
    }

    @Override
    public void cancelar(Reserva reserva) {
        reserva.cambiarEstado(new Cancelada());
    }

    @Override
    public void finalizar(Reserva reserva) {
        throw new IllegalStateException("No se puede finalizar una reserva pendiente.");
    }
}