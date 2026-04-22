package Ficheros;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce tu nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Introduce tu edad: ");
        int edad = sc.nextInt();

        try (FileWriter writer = new FileWriter("usuario.txt")) {
            writer.write("Nombre: " + nombre + "\n");
            writer.write("Edad: " + edad + "\n");
            System.out.println("Datos guardados en usuario.txt");
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero: " + e.getMessage());
        }
    }
}
