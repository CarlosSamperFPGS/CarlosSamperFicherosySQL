package FicherosB;

import Utils.Utils;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class EjercicioB5 {
    public static void main(String[] args) {
        String carpeta = Paths.get("src", "main", "resources", "Diccionario")
                .toAbsolutePath().toString();
        new File(carpeta).mkdirs();

        Map<Character, List<String>> mapa = new TreeMap<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            mapa.put(c, new ArrayList<>());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(Utils.getRutaB("diccionario.txt")))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    char inicial = Character.toUpperCase(linea.charAt(0));
                    if (mapa.containsKey(inicial)) {
                        mapa.get(inicial).add(linea);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer diccionario: " + e.getMessage());
            return;
        }

        for (Map.Entry<Character, List<String>> entrada : mapa.entrySet()) {
            String rutaFichero = carpeta + File.separator + entrada.getKey() + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaFichero))) {
                for (String palabra : entrada.getValue()) {
                    writer.write(palabra);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error al escribir " + entrada.getKey() + ".txt: " + e.getMessage());
            }
        }
        System.out.println("Diccionario generado en: " + carpeta);
    }
}