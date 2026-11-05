package biblioteca;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Sala> salas = List.of(
            new Sala("A", "sala A", 2),
            new Sala("B", "sala B", 4),
            new Sala("C", "sala C", 6),
            new Sala("D", "sala D", 8)
        );
        System.out.println("Salas de la biblioteca:");
        salas.forEach(System.out::println);

        Reserva reserva = new ReservaBuilder()
            .estudiante(new Estudiante("E001", ""))
            .fecha(java.time.LocalDate.now().plusDays(7))
            .horaInicio(java.time.LocalTime.of(14, 0))
            .duracionHoras(2)
            .cantidadPersonas(4)
            .sala(new Sala("B", "sala B", 4))
            .build();

        System.out.println(reserva);
    }
}
