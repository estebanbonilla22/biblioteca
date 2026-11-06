package biblioteca;

public class RegistroEventos implements ObservadorReserva {
    @Override
    public void actualizar(Reserva reserva, String anterior, String nuevo) {
        System.out.printf("Evento registrado | %s: %s → %s%n",
            reserva.getCodigo(), anterior, nuevo);
    }
}
