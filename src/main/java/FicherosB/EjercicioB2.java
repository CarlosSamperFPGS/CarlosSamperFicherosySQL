package FicherosB;

import Utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EjercicioB2 {

    public static void main(String[] args) {
        List<Alumno> alumnos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(Utils.getRutaB("alumnos_notas.txt")))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] partes = linea.split(" ");
                String nombre = partes[0] + " " + partes[1];
                double suma = 0;
                int count = 0;
                for (int i = 2; i < partes.length; i++) {
                    suma += Double.parseDouble(partes[i]);
                    count++;
                }
                double media = suma / count;
                alumnos.add(new Alumno(nombre, media));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        alumnos.sort(Comparator.comparingDouble((Alumno a) -> a.media).reversed());

        System.out.println("Alumnos ordenados por nota media:");
        for (Alumno a : alumnos) {
            System.out.printf("%s: %.2f%n", a.nombre, a.media);
        }
    }
}
