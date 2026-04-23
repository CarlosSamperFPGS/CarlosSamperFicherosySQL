package FicherosB;

import Utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class EjercicioB3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nombre del archivo de entrada (A): ");
        String archivoA = sc.nextLine().trim();
        archivoA = Utils.secureExtension(archivoA);

        System.out.print("Nombre del archivo de salida (B): ");
        String archivoB = sc.nextLine().trim();
        archivoB = Utils.secureExtension(archivoB);

        List<String> lineas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(Utils.getRutaB(archivoA)))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return;
        }

        Collections.sort(lineas);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Utils.getRutaB(archivoB)))) {
            for (String linea : lineas) {
                writer.write(linea);
                writer.newLine();
            }
            System.out.println("Archivo ordenado guardado en: " + archivoB);
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }
    }
}
