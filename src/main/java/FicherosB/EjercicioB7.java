package FicherosB;

import Utils.Utils;

import java.io.*;
import java.util.*;

public class EjercicioB7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre del archivo a analizar: ");
        String archivo = scanner.nextLine().trim();
        archivo = Utils.secureExtension(archivo);

        int numLineas = 0;
        int numPalabras = 0;
        int numCaracteres = 0;
        Hashtable<String, Integer> frecuencias = new Hashtable<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(Utils.getRutaB7(archivo)))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                numLineas++;
                numCaracteres += linea.length();

                String[] palabras = linea.trim().split("\\s+");
                for (String palabra : palabras) {
                    palabra = palabra.replaceAll("[^a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]", "").toLowerCase();
                    if (!palabra.isEmpty()) {
                        numPalabras++;
                        frecuencias.put(palabra, frecuencias.getOrDefault(palabra, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        System.out.println("\n===== ESTADÍSTICAS =====");
        System.out.println("Líneas:     " + numLineas);
        System.out.println("Palabras:   " + numPalabras);
        System.out.println("Caracteres: " + numCaracteres);

        List<Map.Entry<String, Integer>> lista = new ArrayList<>(frecuencias.entrySet());
        lista.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("\nTop 10 palabras más comunes:");
        for (int i = 0; i < Math.min(10, lista.size()); i++) {
            System.out.printf("  %-20s %d veces%n", lista.get(i).getKey(), lista.get(i).getValue());
        }
    }
}