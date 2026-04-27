package DBExercise.hogwarts;

import DBExercise.hogwarts.config.DatabaseConnection;
import DBExercise.hogwarts.operations.Operaciones;
import DBExercise.hogwarts.operations.RepasoSQL;
import DBExercise.hogwarts.modelo.Asignatura;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Operaciones ops = new Operaciones();
        RepasoSQL rSQL = new RepasoSQL();

        // Operaciones CRUD

        List<Asignatura> asignaturas = ops.getAllAsignaturas(); // 1

        System.out.println("\nTodas las asignaturas:");
        System.out.println("─".repeat(60));
        System.out.printf("%-5s %-40s %-20s %-10s%n", "ID", "Nombre", "Aula", "Obligatoria");
        System.out.println("─".repeat(60));
        asignaturas.forEach(a -> System.out.printf("%-5d %-40s %-20s %-10s%n",
                a.getIdAsignatura(), a.getNombre(), a.getAula(),
                a.isObligatoria() ? "Sí" : "No"));

        List<String> gryffindor = ops.getEstudiantesPorCasa("Gryffindor"); // 2

        System.out.println("\nEstudiantes de Gryffindor:");
        System.out.println("─".repeat(35));
        gryffindor.forEach(e -> System.out.println("  " + e));
        System.out.println("  Total: " + gryffindor.size());

        ops.getMascotaDeEstudiante("Hermione", "Granger"); // 3
        ops.getNumEstudiantesPorCasa(); // Operaciones 4
        ops.insertarAsignatura("Magia Oscura Avanzada", "Torre Negra", false); // 5
        ops.modificarAulaAsignatura(17, "Sala Prohibida"); // 6
        ops.eliminarAsignatura(17); // 7


        // RepasoSQL

        rSQL.getNombresProfesores(); // 1
        rSQL.getNombresAlumnosFiltro(); // 2
        rSQL.getNombresAlumnosOrdenado(); // 3
        rSQL.getEstudiantesAgrupadosPorCasa(); // 4
        rSQL.getEstadisticasPociones(); // 5
        rSQL.getYearCursoDistintos(); // 6
        rSQL.getEstudiantesApellidoConP(); // 7
        rSQL.getEstudiantesEn4oY5oYear(); // 8
        rSQL.getEstudiantes5oGryffindorOSlytherin(); // 9
        rSQL.getPrimeros5EstudiantesPorNacimiento(); // 10
        rSQL.getEstudiantesNotaVueloMayorOIgual8(); // 11
        rSQL.insertarNymphadoraTonks(); // 12
        rSQL.actualizarJefeHufflepuff(); // 13
        rSQL.eliminarTomRiddle(); // 14
        rSQL.getEstudiantesConCasa(); // 15
        rSQL.getEstudiantesConMascotasYAsignaturas(); // 16
        rSQL.getEstudiantesSobreMediaEncantamientos(); // 17
        rSQL.getCasasConMediaCalificacionSuperior7(); // 18

        DatabaseConnection.closeConnection();
    }
}
