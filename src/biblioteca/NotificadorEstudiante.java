package biblioteca;

public class NotificadorEstudiante implements ObservadorReserva {
    @Override
    public void actualizar(Reserva reserva, String anterior, String nuevo) {
        System.out.printf("Notificación a %s | %s: %s → %s%n",
            reserva.getEstudiante().getNombre(), reserva.getCodigo(), anterior, nuevo);
    }
}
