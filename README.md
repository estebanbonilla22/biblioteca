# Laboratorio de reservas de biblioteca

Prototipo de consola en Java. Las salas disponibles se simulan con un catálogo fijo.
Las reservas viven en memoria durante la ejecución.

Desde la raíz del repositorio, con JDK 11 o superior:

```sh
javac --release 11 -encoding UTF-8 -d /tmp/biblioteca-clases src/biblioteca/*.java pruebas/biblioteca/*.java
java -cp /tmp/biblioteca-clases biblioteca.Main
java -cp /tmp/biblioteca-clases biblioteca.PruebasLaboratorio
java -cp /tmp/biblioteca-clases biblioteca.PruebaEstados
```
