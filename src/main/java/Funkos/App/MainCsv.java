package Funkos.App;

import Funkos.Repositorio.FunkoRepositoryCsv;
import Funkos.Servicio.FunkoService;
import Funkos.UI.FunkoConsoleUI;

import java.io.IOException;


public class MainCsv {

    private static final String RUTA_CSV = "src/main/resources/funkos.csv";

    public static void main(String[] args) {
        try {
            FunkoService    service = new FunkoService(new FunkoRepositoryCsv(RUTA_CSV));
            FunkoConsoleUI  ui      = new FunkoConsoleUI(service);
            ui.iniciar();
        } catch (IOException e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
        }
    }
}
