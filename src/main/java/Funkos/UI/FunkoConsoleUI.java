package Funkos.UI;

import Funkos.Modelo.Funko;
import Funkos.Servicio.FunkoService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class FunkoConsoleUI {

    private static final int ANYO_FILTRO = 2023;

    private final FunkoService service;
    private final Scanner      scanner;

    public FunkoConsoleUI(FunkoService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║     GESTIÓN DE FUNKOS        ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println("Funkos cargados: " + service.getTodos().size());

        boolean ejecutando = true;
        while (ejecutando) {
            mostrarMenu();
            int opcion = leerOpcion();
            ejecutando = procesarOpcion(opcion);
        }
        System.out.println("\n¡Hasta pronto!");
        scanner.close();
    }

    // Menú

    private void mostrarMenu() {
        System.out.println("─".repeat(35));
        System.out.println("  1. Añadir funko");
        System.out.println("  2. Borrar funko");
        System.out.println("  3. Mostrar todos los funkos");
        System.out.println("  4. Mostrar el funko más caro");
        System.out.println("  5. Media de precios");
        System.out.println("  6. Funkos agrupados por modelo");
        System.out.println("  7. Funkos de " + ANYO_FILTRO);
        System.out.println("  0. Salir");
        System.out.println("─".repeat(35));
        System.out.print("  Elige una opción: ");
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private boolean procesarOpcion(int opcion) {
        switch (opcion) {
            case 1  -> anyadirFunko();
            case 2  -> borrarFunko();
            case 3  -> mostrarTodos();
            case 4  -> mostrarMasCaro();
            case 5  -> mostrarMediaPrecios();
            case 6  -> mostrarAgrupadosPorModelo();
            case 7  -> mostrarFunkosDeAnyo();
            case 0  -> { return false; }
            default -> System.out.println("Opción no válida.");
        }
        return true;
    }

    // Opciones

    private void anyadirFunko() {
        System.out.println("\n── Añadir nuevo Funko ──");
        try {
            System.out.print("Nombre           : ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Modelo " + Arrays.toString(Funko.Modelo.values()) + ": ");
            Funko.Modelo modelo = Funko.Modelo.valueOf(scanner.nextLine().trim().toUpperCase());

            System.out.print("Precio           : ");
            double precio = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Fecha lanzamiento (AAAA-MM-DD): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine().trim());

            Funko nuevo = new Funko(UUID.randomUUID(), nombre, modelo, precio, fecha);
            service.anyadirFunko(nuevo);
            System.out.println("Funko añadido: " + nuevo);

        } catch (IllegalArgumentException e) {
            System.out.println("Datos incorrectos: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    private void borrarFunko() {
        System.out.println("\n── Borrar Funko ──");
        System.out.print("Nombre del funko a borrar: ");
        String nombre = scanner.nextLine().trim();
        try {
            boolean ok = service.borrarFunko(nombre);
            System.out.println(ok
                ? " Funko '" + nombre + "' eliminado."
                : " No se encontró un funko con ese nombre.");
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    private void mostrarTodos() {
        List<Funko> todos = service.getTodos();
        System.out.println("\n── Todos los Funkos (" + todos.size() + ") ──");
        imprimirCabecera();
        todos.forEach(this::imprimirFunko);
    }

    private void mostrarMasCaro() {
        System.out.println("\n── Funko más caro ──");
        service.getFunkoPlusCaro().ifPresentOrElse(
            f -> {
                imprimirCabecera();
                imprimirFunko(f);
            },
            () -> System.out.println("No hay funkos.")
        );
    }

    private void mostrarMediaPrecios() {
        System.out.printf("%n── Media de precios: %.2f€ ──%n", service.getMediaPrecios());
    }

    private void mostrarAgrupadosPorModelo() {
        System.out.println("\n── Funkos agrupados por modelo ──");
        service.getFunkosAgrupadosPorModelo()
               .forEach((modelo, lista) -> {
                   System.out.println("\n  " + modelo + " (" + lista.size() + "):");
                   lista.forEach(f -> System.out.printf(
                       "    %-35s %.2f€  %s%n",
                       f.getNombre(), f.getPrecio(), f.getFechaLanzamiento()));
               });
    }

    private void mostrarFunkosDeAnyo() {
        List<Funko> lista = service.getFunkosDeAnyo(ANYO_FILTRO);
        System.out.println("\n── Funkos de " + ANYO_FILTRO + " (" + lista.size() + ") ──");
        imprimirCabecera();
        lista.forEach(this::imprimirFunko);
    }

    // ── Utilidades de formato ─────────────────────────────────────

    private void imprimirCabecera() {
        System.out.printf("  %-36s %-35s %-8s %-7s %-12s%n",
                "COD", "NOMBRE", "MODELO", "PRECIO", "LANZAMIENTO");
        System.out.println("  " + "─".repeat(100));
    }

    private void imprimirFunko(Funko f) {
        System.out.printf("  %-36s %-35s %-8s %6.2f€  %-12s%n",
                f.getCod(), f.getNombre(), f.getModelo(),
                f.getPrecio(), f.getFechaLanzamiento());
    }
}
