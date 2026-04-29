package Funkos.Repositorio;

import Funkos.Modelo.Funko;

import java.io.IOException;
import java.util.List;

public interface FunkoRepository {
    List<Funko> loadFunkos() throws IOException;
    void saveFunkos(List<Funko> funkos) throws IOException;
}
