package Funkos.App;

import Funkos.Repositorio.FunkoRepositoryBin;
import Funkos.Servicio.FunkoService;
import Funkos.UI.FunkoConsoleUI;

import java.io.IOException;


public class MainBin {

    private static final String RUTA_BIN = "src/main/resources/funkos.dat";

    public static void main(String[] args) {
        try {
            FunkoService    service = new FunkoService(new FunkoRepositoryBin(RUTA_BIN));
            FunkoConsoleUI  ui      = new FunkoConsoleUI(service);
            ui.iniciar();
        } catch (IOException e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
        }
    }
}
