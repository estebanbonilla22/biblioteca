package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var salas = new RepositorioSalasMemoria();
        var reservas = new RepositorioReservasMemoria();
        var fachada = new ReservaFacade(salas, reservas,
            List.of(new NotificadorEstudiante(), new RegistroEventos()));
        var porCapacidad = new SeleccionPorCapacidad();
        var fecha = LocalDate.now().plusDays(7);

        System.out.println("=== ESCENARIO PRINCIPAL ===");
        Reserva laura = fachada.realizarReserva(new Estudiante("E001", "Laura"),
            fecha, LocalTime.of(14, 0), 2, 4, porCapacidad).orElseThrow();
        System.out.println(laura);
        laura.cancelar();
        System.out.println(laura);
        try {
            laura.confirmar();
        } catch (IllegalStateException error) {
            System.out.println("Operación rechazada: " + error.getMessage());
        }

        System.out.println("\n=== SALA PREFERIDA ===");
        fachada.realizarReserva(new Estudiante("E002", "Andrés"),
            fecha.plusDays(1), LocalTime.of(10, 0), 2, 4,
            new SeleccionSalaPreferida("C")).ifPresent(System.out::println);

        System.out.println("\n=== SOLICITUD PARA 10 PERSONAS ===");
        fachada.realizarReserva(new Estudiante("E003", "Camila"),
            fecha.plusDays(2), LocalTime.of(9, 0), 2, 10, porCapacidad);

        System.out.println("\n=== FINALIZACIÓN ===");
        Reserva samuel = fachada.realizarReserva(new Estudiante("E004", "Samuel"),
            fecha.plusDays(3), LocalTime.of(11, 0), 1, 2, porCapacidad).orElseThrow();
        samuel.finalizar();
        System.out.println(samuel);
        System.out.println("Reservas registradas: " + reservas.obtenerTodas().size());
    }
}
