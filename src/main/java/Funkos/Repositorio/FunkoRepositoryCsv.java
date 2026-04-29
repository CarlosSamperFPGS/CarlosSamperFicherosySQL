package Funkos.Repositorio;

import Funkos.Modelo.Funko;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class FunkoRepositoryCsv implements FunkoRepository {

    private static final String CABECERA   = "COD,NOMBRE,MODELO,PRECIO,FECHA_LANZAMIENTO";
    private static final String SEPARADOR  = ",";

    private final String rutaFichero;

    public FunkoRepositoryCsv(String rutaFichero) {
        this.rutaFichero = rutaFichero;
    }

    @Override
    public List<Funko> loadFunkos() throws IOException {
        List<Funko> funkos = new ArrayList<>();
        Path path = Path.of(rutaFichero);

        if (!Files.exists(path)) return funkos;

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { primeraLinea = false; continue; } // saltar cabecera
                if (linea.isBlank()) continue;
                parsearLinea(linea).ifPresent(funkos::add);
            }
        }
        return funkos;
    }

    @Override
    public void saveFunkos(List<Funko> funkos) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(Path.of(rutaFichero))) {
            bw.write(CABECERA);
            bw.newLine();
            for (Funko f : funkos) {
                bw.write(funkoALinea(f));
                bw.newLine();
            }
        }
    }

    // Helpers privados

    private Optional<Funko> parsearLinea(String linea) {
        try {
            String[] partes = linea.split(SEPARADOR);
            UUID      cod    = UUID.fromString(partes[0].trim());
            String    nombre = partes[1].trim();
            Funko.Modelo modelo = Funko.Modelo.valueOf(partes[2].trim());
            double    precio = Double.parseDouble(partes[3].trim());
            LocalDate fecha  = LocalDate.parse(partes[4].trim());
            return Optional.of(new Funko(cod, nombre, modelo, precio, fecha));
        } catch (Exception e) {
            System.err.println("Línea ignorada por formato incorrecto: " + linea);
            return Optional.empty();
        }
    }

    private String funkoALinea(Funko f) {
        return String.join(SEPARADOR,
                f.getCod().toString(),
                f.getNombre(),
                f.getModelo().name(),
                String.format("%.2f", f.getPrecio()),
                f.getFechaLanzamiento().toString());
    }
}
