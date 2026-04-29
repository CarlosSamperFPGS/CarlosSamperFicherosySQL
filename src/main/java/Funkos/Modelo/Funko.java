package Funkos.Modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Funko implements Serializable {

    private static final long serialVersionUID = 1L;

    private final UUID cod;
    private String nombre;
    private Modelo modelo;
    private double precio;
    private LocalDate fechaLanzamiento;

    public enum Modelo {
        MARVEL, DISNEY, ANIME, OTROS
    }

    public Funko(UUID cod, String nombre, Modelo modelo, double precio, LocalDate fechaLanzamiento) {
        this.cod = cod;
        this.nombre = nombre;
        this.modelo = modelo;
        this.precio = precio;
        this.fechaLanzamiento = fechaLanzamiento;
    }

    // Getters
    public UUID getCod() { return cod; }
    public String getNombre() { return nombre; }
    public Modelo getModelo() { return modelo; }
    public double getPrecio() { return precio; }
    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setModelo(Modelo modelo) { this.modelo = modelo; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setFechaLanzamiento(LocalDate fechaLanz) { this.fechaLanzamiento = fechaLanz; }

    @Override
    public String toString() {
        return String.format("Funko{cod=%s, nombre='%s', modelo=%s, precio=%.2f€, lanzamiento=%s}",
                cod, nombre, modelo, precio, fechaLanzamiento);
    }
}
