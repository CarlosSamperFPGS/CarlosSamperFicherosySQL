package Funkos.Servicio;

import Funkos.Modelo.Funko;
import Funkos.Repositorio.FunkoRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class FunkoService {

    private final FunkoRepository repositorio;
    private final List<Funko>     funkos;

    public FunkoService(FunkoRepository repositorio) throws IOException {
        this.repositorio = repositorio;
        this.funkos      = repositorio.loadFunkos();
    }

    // CRUD

    public void anyadirFunko(Funko funko) throws IOException {
        funkos.add(funko);
        repositorio.saveFunkos(funkos);
    }

    public boolean borrarFunko(String nombre) throws IOException {
        boolean eliminado = funkos.removeIf(
                f -> f.getNombre().equalsIgnoreCase(nombre));
        if (eliminado) repositorio.saveFunkos(funkos);
        return eliminado;
    }

    public List<Funko> getTodos() {
        return Collections.unmodifiableList(funkos);
    }

    // Consultas

    public Optional<Funko> getFunkoPlusCaro() {
        return funkos.stream()
                .max(Comparator.comparingDouble(Funko::getPrecio));
    }

    public double getMediaPrecios() {
        return funkos.stream()
                .mapToDouble(Funko::getPrecio)
                .average()
                .orElse(0.0);
    }

    public Map<Funko.Modelo, List<Funko>> getFunkosAgrupadosPorModelo() {
        return funkos.stream()
                .collect(Collectors.groupingBy(Funko::getModelo));
    }

    public List<Funko> getFunkosDeAnyo(int anyo) {
        return funkos.stream()
                .filter(f -> f.getFechaLanzamiento().getYear() == anyo)
                .collect(Collectors.toList());
    }
}
