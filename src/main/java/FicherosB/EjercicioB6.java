package FicherosB;

import Utils.Utils;

import java.io.*;

public class EjercicioB6 {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("Introduce el número a buscar: ");
        String patron = scanner.nextLine().trim();

        StringBuilder pi = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(Utils.getRutaB("pi-million.txt")))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                pi.append(linea.trim());
            }
        } catch (IOException e) {
            System.out.println("Error al leer pi-million.txt: " + e.getMessage());
            return;
        }

        int posicion = Utils.buscar(pi.toString(), patron);

        if (posicion != -1) {
            System.out.println("El número " + patron + " aparece en la posición " + posicion + " de PI.");
        } else {
            System.out.println("El número " + patron + " NO aparece en el primer millón de decimales de PI.");
        }
    }


}
