package Ficheros;

import Utils.Utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Ejercicio4 {
    public static void main(String[] args) {
        Persona persona = new Persona("Ximo", 25);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Utils.getRuta("persona.dat")))) {
            oos.writeObject(persona);
            System.out.println("Persona serializada correctamente.");
        } catch (IOException e) {
            System.out.println("Error al serializar: " + e.getMessage());
        }
    }
}