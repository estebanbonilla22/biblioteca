package biblioteca;
import java.util.List;

public class main{
    public static void main(String[] args) {
        List<Sala> salas = List.of(
            new Sala("A","sala A",2),
            new Sala("B","sala B",4),
            new Sala("C","sala C",6),
            new Sala("D","sala D",8)
        );
        System.out.println("Salas de la biblioteca:");
        salas.forEach(System.out::println);
    }
}