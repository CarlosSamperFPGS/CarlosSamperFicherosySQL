package Ficheros;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Ejercicio5 {
    public static void main(String[] args) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("persona.dat"))) {
            Persona persona = (Persona) ois.readObject();
            System.out.println("Datos leídos: " + persona);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al deserializar: " + e.getMessage());
        }
    }
}
