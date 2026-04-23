package FicherosB;

import Utils.Utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EjercicioB1 {
    public static void main(String[] args) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        try (BufferedReader reader = new BufferedReader(new FileReader(Utils.getRutaB("numeros.txt")))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    int numero = Integer.parseInt(linea);
                    if (numero > max) max = numero;
                    if (numero < min) min = numero;
                }
            }
            System.out.println("Máximo: " + max);
            System.out.println("Mínimo: " + min);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }
}