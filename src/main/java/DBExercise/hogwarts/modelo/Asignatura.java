package DBExercise.hogwarts.modelo;

public class Asignatura {
    private int     idAsignatura;
    private String  nombre;
    private String  aula;
    private boolean obligatoria;

    public Asignatura(int idAsignatura, String nombre, String aula, boolean obligatoria) {
        this.idAsignatura = idAsignatura;
        this.nombre       = nombre;
        this.aula         = aula;
        this.obligatoria  = obligatoria;
    }

    public Asignatura() {}

    // Getters y Setters
    public int getIdAsignatura() { return idAsignatura; }
    public void setIdAsignatura(int idAsignatura) { this.idAsignatura = idAsignatura; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getAula() { return aula; }
    public void setAula(String aula) { this.aula = aula; }

    public boolean isObligatoria() { return obligatoria; }
    public void setObligatoria(boolean obligatoria) { this.obligatoria = obligatoria; }

    @Override
    public String toString() {
        return "Nombre: " + nombre + " Aula: " + aula + " Es Obligatoria: " + obligatoria;
    }
}
