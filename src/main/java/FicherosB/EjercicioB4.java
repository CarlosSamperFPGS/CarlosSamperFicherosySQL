package FicherosB;

import Utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class EjercicioB4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Cuántos nombres deseas generar? ");
        int cantidad = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Nombre del archivo destino: ");
        String archivoDestino = scanner.nextLine().trim();
        archivoDestino = Utils.secureExtension(archivoDestino);

        List<String> nombres = Utils.cargarLineas("usa_nombres.txt");
        List<String> apellidos = Utils.cargarLineas("usa_apellidos.txt");

        if (nombres.isEmpty() || apellidos.isEmpty()) {
            System.out.println("Error: no se pudieron cargar los archivos de nombres/apellidos.");
            return;
        }

        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Utils.getRutaB(archivoDestino), true))) {
            for (int i = 0; i < cantidad; i++) {
                String nombre = nombres.get(random.nextInt(nombres.size()));
                String apellido1 = apellidos.get(random.nextInt(apellidos.size()));
                String apellido2 = apellidos.get(random.nextInt(apellidos.size()));
                writer.write(nombre + " " + apellido1 + " " + apellido2);
                writer.newLine();
            }
            System.out.println(cantidad + " nombres añadidos a " + archivoDestino);
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }
    }
}
