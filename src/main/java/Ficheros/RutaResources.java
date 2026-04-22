package Ficheros;

import java.nio.file.Path;
import java.nio.file.Paths;

public class RutaResources {
    public static String getRuta(String nombreFichero) {
        return Paths.get("src", "main", "resources", nombreFichero).toAbsolutePath().toString();
    }
}