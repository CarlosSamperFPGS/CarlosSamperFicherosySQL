package Ficheros;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Ejercicio6Guardar {
    public static void main(String[] args) {
        ArrayList<Persona> personas = new ArrayList<>();
        personas.add(new Persona("Ana", 34));
        personas.add(new Persona("Diana", 27));
        personas.add(new Persona("Carlos", 23));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("personas.dat"))) {
            oos.writeObject(personas);
            System.out.println("Lista guardada correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }
}