package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PruebaEstados {
    public static void main(String[] args) {
        var eventos = new ArrayList<String>();
        var reserva = nuevaReserva();
        reserva.agregarObservador((r, anterior, nuevo) -> eventos.add(anterior + " → " + nuevo));
        verificar("Pendiente", reserva.getNombreEstado());
        reserva.confirmar();
        verificar("Confirmada", reserva.getNombreEstado());
        reserva.cancelar();
        verificar("Cancelada", reserva.getNombreEstado());
        verificarRechazo(reserva::confirmar);
        verificar("Cancelada", reserva.getNombreEstado());
        verificar(List.of("Pendiente → Confirmada", "Confirmada → Cancelada"), eventos);

        var otra = nuevaReserva();
        otra.agregarObservador((r, anterior, nuevo) -> eventos.add(anterior + " → " + nuevo));
        otra.confirmar();
        otra.finalizar();
        verificar("Finalizada", otra.getNombreEstado());
        verificarRechazo(otra::cancelar);
        verificar("Finalizada", otra.getNombreEstado());
        verificar(List.of("Pendiente → Confirmada", "Confirmada → Cancelada",
                          "Pendiente → Confirmada", "Confirmada → Finalizada"), eventos);
    }

    private static Reserva nuevaReserva() {
        return new ReservaBuilder()
            .estudiante(new Estudiante("E001", "Laura"))
            .fecha(LocalDate.now().plusDays(1))
            .horaInicio(LocalTime.of(14, 0))
            .duracionHoras(2)
            .cantidadPersonas(4)
            .sala(new Sala("B", "Sala B", 4))
            .build();
    }

    private static void verificar(Object esperado, Object real) {
        if (!esperado.equals(real)) {
            throw new AssertionError("Se esperaba " + esperado + " y se obtuvo " + real);
        }
        System.out.println("Verificado: " + real);
    }

    private static void verificarRechazo(Runnable accion) {
        try {
            accion.run();
        } catch (IllegalStateException error) {
            System.out.println("Rechazo verificado: " + error.getMessage());
            return;
        }
        throw new AssertionError("La transición debía rechazarse.");
    }
}
