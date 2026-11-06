package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PruebasLaboratorio {
    public static void main(String[] args) {
        var salas = new RepositorioSalasMemoria();
        var reservas = new RepositorioReservasMemoria();
        var eventos = new ArrayList<String>();
        ObservadorReserva notificador = (r, a, n) -> {
            new NotificadorEstudiante().actualizar(r, a, n);
            eventos.add("Aviso " + n);
        };
        ObservadorReserva registro = (r, a, n) -> {
            new RegistroEventos().actualizar(r, a, n);
            eventos.add("Registro " + n);
        };
        var fachada = new ReservaFacade(salas, reservas, List.of(notificador, registro));
        var fecha = LocalDate.now().plusDays(7);
        var hora = LocalTime.of(14, 0);
        var estudiante = new Estudiante("E001", "Laura");
        var porCapacidad = new SeleccionPorCapacidad();
        var disponibles = salas.obtenerDisponibles();
        var builder = new ReservaBuilder().fecha(fecha).horaInicio(hora)
            .duracionHoras(2).cantidadPersonas(4).sala(disponibles.get(1));

        Reserva inicial = builder.estudiante(estudiante).build();
        verificar("P01", inicial.getEstudiante() == estudiante
            && inicial.getFecha().equals(fecha) && inicial.getHoraInicio().equals(hora)
            && inicial.getDuracionHoras() == 2 && inicial.getCantidadPersonas() == 4
            && inicial.getSala() == disponibles.get(1)
            && "Pendiente".equals(inicial.getNombreEstado()), "Reserva válida en estado Pendiente");
        String error = rechazo(() -> builder.estudiante(null).build());
        verificar("P02", error.contains("datos obligatorios"), error);
        verificar("P03", porCapacidad.seleccionar(disponibles, 4).orElseThrow()
            .getCodigo().equals("B"), "Capacidad para 4 → Sala B");
        verificar("P04", new SeleccionSalaPreferida("C").seleccionar(disponibles, 4)
            .orElseThrow().getCodigo().equals("C"), "Preferida C para 4 → Sala C");
        verificar("P05", new SeleccionSalaPreferida("B").seleccionar(disponibles, 7)
            .isEmpty(), "Preferida B para 7 → sin sala");

        Reserva reserva = fachada.realizarReserva(estudiante, fecha, hora, 2, 4,
            porCapacidad).orElseThrow();
        verificar("P06", reservas.obtenerTodas().equals(List.of(reserva))
            && reserva.getNombreEstado().equals("Confirmada"), "Guardada y confirmada");
        verificar("P07", eventos.equals(List.of("Aviso Confirmada", "Registro Confirmada")),
            "Ambos observadores recibieron la confirmación");

        eventos.clear();
        reserva.cancelar();
        verificar("P08", reserva.getNombreEstado().equals("Cancelada")
            && eventos.equals(List.of("Aviso Cancelada", "Registro Cancelada")),
            "Cancelada con dos avisos");
        eventos.clear();
        error = rechazo(reserva::confirmar);
        verificar("P09", reserva.getNombreEstado().equals("Cancelada") && eventos.isEmpty(),
            error + " Eventos: " + eventos.size());

        verificar("P10", fachada.realizarReserva(estudiante, fecha, hora, 2, 10,
            porCapacidad).isEmpty() && reservas.obtenerTodas().size() == 1 && eventos.isEmpty(),
            "Solicitud para 10 sin reserva, sin registro y sin eventos");

        Reserva otra = fachada.realizarReserva(estudiante, fecha, hora, 1, 2,
            porCapacidad).orElseThrow();
        eventos.clear();
        otra.finalizar();
        verificar("P11", otra.getNombreEstado().equals("Finalizada")
            && eventos.equals(List.of("Aviso Finalizada", "Registro Finalizada")),
            "Finalizada con dos avisos");
    }

    private static void verificar(String id, boolean condicion, String detalle) {
        if (!condicion) {
            throw new AssertionError(id + " falló: " + detalle);
        }
        System.out.println(id + " OK | " + detalle);
    }

    private static String rechazo(Runnable operacion) {
        try {
            operacion.run();
        } catch (IllegalStateException error) {
            return error.getMessage();
        }
        throw new AssertionError("Se esperaba IllegalStateException.");
    }
}
