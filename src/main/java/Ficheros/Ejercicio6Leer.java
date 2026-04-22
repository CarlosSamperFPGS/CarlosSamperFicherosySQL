package Ficheros;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Ejercicio6Leer {
    public static void main(String[] args) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("personas.dat"))) {
            @SuppressWarnings("unchecked")
            ArrayList<Persona> personas = (ArrayList<Persona>) ois.readObject();
            for (Persona p : personas) {
                System.out.println(p);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }
}