package Ficheros;

import Utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio3 {
    public static void main(String[] args) {
        int totalPalabras = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(Utils.getRuta("texto.txt")))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] palabras = linea.trim().split("\\s+");
                    totalPalabras += palabras.length;
                }
            }
            System.out.println("Total de palabras: " + totalPalabras);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }
}
