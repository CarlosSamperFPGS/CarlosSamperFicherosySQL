package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String getRuta(String nombreFichero) {
        return Paths.get("src", "main", "resources", nombreFichero).toAbsolutePath().toString();
    }

    public static String getRutaB(String nombreFichero) {
        return Paths.get("src", "main", "Documentos", nombreFichero).toAbsolutePath().toString();
    }

    public static String getRutaB7(String nombreFichero) {
        return Paths.get("src", "main", "Documentos", "Libros", nombreFichero).toAbsolutePath().toString();
    }

    public static String secureExtension(String nombre) {
        if (!nombre.endsWith(".txt")) {
            nombre += ".txt";
        }
        return nombre;
    }

    public static List<String> cargarLineas(String fichero) {
        List<String> lista = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(Utils.getRutaB(fichero)))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) lista.add(linea.trim());
            }
        } catch (IOException e) {
            System.out.println("Error al leer " + fichero + ": " + e.getMessage());
        }
        return lista;
    }
    public static int buscar(String texto, String patron) {
        int n = texto.length();
        int m = patron.length();

        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while (j < m && texto.charAt(i + j) == patron.charAt(j)) {
                j++;
            }
            if (j == m) return i;
        }
        return -1;
    }
}
