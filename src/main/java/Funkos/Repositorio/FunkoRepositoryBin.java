package Funkos.Repositorio;

import Funkos.Modelo.Funko;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FunkoRepositoryBin implements FunkoRepository {

    private final String rutaFichero;

    public FunkoRepositoryBin(String rutaFichero) {
        this.rutaFichero = rutaFichero;
    }

    @Override
    public List<Funko> loadFunkos() throws IOException {
        Path path = Path.of(rutaFichero);
        if (!Files.exists(path)) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(Files.newInputStream(path)))) {
            return (List<Funko>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Error al deserializar los funkos: " + e.getMessage(), e);
        }
    }

    @Override
    public void saveFunkos(List<Funko> funkos) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(Files.newOutputStream(
                        Path.of(rutaFichero),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING)))) {
            oos.writeObject(funkos);
        }
    }
}
