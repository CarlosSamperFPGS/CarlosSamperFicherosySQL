package DBExercise.hogwarts.operations;

import DBExercise.hogwarts.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepasoSQL {

    public void getNombresProfesores() {
        String sql = """
                SELECT nombre, apellido
                FROM Profesor
                ORDER BY apellido
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n 1. Profesores de Hogwarts:");
            System.out.println("─".repeat(35));
            while (rs.next())
                System.out.printf("  %s %s%n", rs.getString("nombre"), rs.getString("apellido"));

        } catch (SQLException e) { System.err.println("❌ Error: " + e.getMessage()); }
    }

    public void getNombresAlumnosFiltro() {
        String sql = """
                SELECT nombre, apellido, fecha_nacimiento
                FROM Estudiante
                WHERE fecha_nacimiento > '1980-01-01'
                ORDER BY fecha_nacimiento
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n 2. Estudiantes nacidos después del 01/01/1980:");
            System.out.println("─".repeat(50));
            System.out.printf("  %-30s %-15s%n", "Nombre", "Fecha nacimiento");
            System.out.println("─".repeat(50));
            while (rs.next())
                System.out.printf("  %-30s %-15s%n",
                        rs.getString("nombre") + " " + rs.getString("apellido"),
                        rs.getDate("fecha_nacimiento"));

        } catch (SQLException e) { System.err.println(" Error: " + e.getMessage()); }
    }

    public void getNombresAlumnosOrdenado() {
        String sql = """
                SELECT nombre, apellido, fecha_nacimiento
                FROM Estudiante
                ORDER BY fecha_nacimiento ASC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n 3. Estudiantes ordenados por fecha de nacimiento ascendente");
            System.out.println("─".repeat(50));
            System.out.printf("  %-30s %-15s%n", "Nombre", "Fecha nacimiento");
            System.out.println("─".repeat(50));
            while (rs.next())
                System.out.printf("  %-30s %-15s%n",
                        rs.getString("nombre") + " " + rs.getString("apellido"),
                        rs.getDate("fecha_nacimiento"));

        } catch (SQLException e) { System.err.println(" Error: " + e.getMessage()); }
    }

    public void getEstudiantesAgrupadosPorCasa() {
        String sql = """
                SELECT c.nombre AS casa, COUNT(e.id_estudiante) AS total
                FROM Casa c
                LEFT JOIN Estudiante e ON c.id_casa = e.id_casa
                GROUP BY c.nombre
                ORDER BY total DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n 4. Estudiantes por casa (GROUP BY):");
            System.out.println("─".repeat(35));
            System.out.printf("  %-30s %-10s%n", "Casa", "Nº Estudiantes");
            System.out.println("─".repeat(35));
            while (rs.next())
                System.out.printf("  %-20s %d%n", rs.getString("casa"), rs.getInt("total"));

        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void getEstadisticasPociones() {
        String sql = """
                SELECT ROUND(AVG(ea.calificacion)::numeric, 2) AS media,
                       MAX(ea.calificacion) AS maxima
                FROM Estudiante_Asignatura ea
                JOIN Asignatura a ON ea.id_asignatura = a.id_asignatura
                WHERE LOWER(a.nombre) = 'pociones'
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n 5. Estadísticas de Pociones:");
            System.out.println("─".repeat(35));
            if (rs.next()) {
                System.out.println("  Media  : " + rs.getDouble("media"));
                System.out.println("  Máxima : " + rs.getDouble("maxima"));
            }

        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void getYearCursoDistintos() {
        String sql = "SELECT DISTINCT anyo_curso FROM Estudiante ORDER BY anyo_curso";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n6. Años de curso (DISTINCT):");
            System.out.println("─".repeat(25));
            while (rs.next())
                System.out.println("  Año " + rs.getInt("anyo_curso"));

        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void getEstudiantesApellidoConP() {
        String sql = """
                SELECT nombre, apellido
                FROM Estudiante
                WHERE apellido LIKE 'P%'
                ORDER BY apellido
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n 7. Estudiantes con apellido que empieza por 'P' (LIKE):");
            System.out.println("─".repeat(35));
            while (rs.next())
                System.out.printf("  %s %s%n", rs.getString("nombre"), rs.getString("apellido"));

        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void getEstudiantesEn4oY5oYear() {
        String sql = """
                SELECT nombre, apellido, anyo_curso
                FROM Estudiante
                WHERE anyo_curso IN (4, 5)
                ORDER BY anyo_curso, apellido
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n8. Estudiantes en 4º o 5º año (IN):");
            System.out.println("─".repeat(40));
            System.out.printf("  %-30s %-5s%n", "Nombre", "Año");
            System.out.println("─".repeat(40));
            while (rs.next())
                System.out.printf("  %-30s %dº%n",
                        rs.getString("nombre") + " " + rs.getString("apellido"),
                        rs.getInt("anyo_curso"));

        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void getEstudiantes5oGryffindorOSlytherin() {
        String sql = """
                SELECT e.nombre, e.apellido, c.nombre AS casa
                FROM Estudiante e
                JOIN Casa c ON e.id_casa = c.id_casa
                WHERE e.anyo_curso = 5
                  AND (LOWER(c.nombre) = 'gryffindor' OR LOWER(c.nombre) = 'slytherin')
                ORDER BY c.nombre, e.apellido
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n 9. 5º año en Gryffindor o Slytherin (AND/OR):");
            System.out.println("─".repeat(45));
            System.out.printf("  %-30s %-15s%n", "Nombre", "Casa");
            System.out.println("─".repeat(45));
            while (rs.next())
                System.out.printf("  %-30s %-15s%n",
                        rs.getString("nombre") + " " + rs.getString("apellido"),
                        rs.getString("casa"));

        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void getPrimeros5EstudiantesPorNacimiento() {
        String sql = """
                SELECT nombre, apellido, fecha_nacimiento
                FROM Estudiante
                ORDER BY fecha_nacimiento ASC
                LIMIT 5
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n10. Primeros 5 estudiantes por nacimiento (LIMIT):");
            System.out.println("─".repeat(50));
            System.out.printf("  %-30s %-15s%n", "Nombre", "Nacimiento");
            System.out.println("─".repeat(50));
            while (rs.next())
                System.out.printf("  %-30s %-15s%n",
                        rs.getString("nombre") + " " + rs.getString("apellido"),
                        rs.getDate("fecha_nacimiento"));

        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void getEstudiantesNotaVueloMayorOIgual8() {
        String sql = """
                SELECT e.nombre, e.apellido, ea.calificacion
                FROM Estudiante e
                JOIN Estudiante_Asignatura ea ON e.id_estudiante = ea.id_estudiante
                JOIN Asignatura a ON ea.id_asignatura = a.id_asignatura
                WHERE LOWER(a.nombre) = 'vuelo'
                  AND ea.calificacion >= 8
                ORDER BY ea.calificacion DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n11. Estudiantes con nota >= 8 en Vuelo:");
            System.out.println("─".repeat(45));
            System.out.printf("  %-30s %-10s%n", "Nombre", "Nota");
            System.out.println("─".repeat(45));
            while (rs.next())
                System.out.printf("  %-30s %.1f%n",
                        rs.getString("nombre") + " " + rs.getString("apellido"),
                        rs.getDouble("calificacion"));

        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void insertarNymphadoraTonks() {
        String sql = """
                INSERT INTO Estudiante (nombre, apellido, id_casa, anyo_curso, fecha_nacimiento)
                VALUES ('Nymphadora', 'Tonks', 4, 7, '1973-11-25')
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next())
                System.out.println("\n12. Nymphadora Tonks insertada con ID: " + keys.getInt(1));

        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void actualizarJefeHufflepuff() {
        String sql = """
                UPDATE Casa
                SET id_jefe = (SELECT id_profesor FROM Profesor
                               WHERE nombre = 'Pomona' AND apellido = 'Sprout')
                WHERE LOWER(nombre) = 'hufflepuff'
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int filas = ps.executeUpdate();
            System.out.println(filas > 0
                    ? "\n 13. Jefe de Hufflepuff actualizado a Pomona Sprout."
                    : "\n13. No se encontró Hufflepuff.");

        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void eliminarTomRiddle() {
        String sql = """
                DELETE FROM Estudiante
                WHERE LOWER(nombre) = 'tom' AND LOWER(apellido) = 'riddle'
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int filas = ps.executeUpdate();
            System.out.println(filas > 0
                    ? "\n  14. Tom Riddle eliminado correctamente."
                    : "\n  14. Tom Riddle no encontrado en la BD.");

        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void getEstudiantesConCasa() {
        String sql = """
                SELECT e.nombre, e.apellido, c.nombre AS casa
                FROM Estudiante e
                JOIN Casa c ON e.id_casa = c.id_casa
                ORDER BY c.nombre, e.apellido
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n15. Estudiantes con su casa (JOIN):");
            System.out.println("─".repeat(50));
            System.out.printf("  %-30s %-15s%n", "Nombre", "Casa");
            System.out.println("─".repeat(50));
            while (rs.next())
                System.out.printf("  %-30s %-15s%n",
                        rs.getString("nombre") + " " + rs.getString("apellido"),
                        rs.getString("casa"));

        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void getEstudiantesConMascotasYAsignaturas() {
        String sql = """
                SELECT DISTINCT e.nombre, e.apellido,
                       m.nombre AS mascota,
                       a.nombre AS asignatura
                FROM Estudiante e
                LEFT JOIN Mascota m ON e.id_estudiante = m.id_estudiante
                LEFT JOIN Estudiante_Asignatura ea ON e.id_estudiante = ea.id_estudiante
                LEFT JOIN Asignatura a ON ea.id_asignatura = a.id_asignatura
                ORDER BY e.apellido, a.nombre
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n 16. Estudiantes con mascotas y asignaturas (LEFT JOIN):");
            System.out.println("─".repeat(75));
            System.out.printf("  %-25s %-15s %-30s%n", "Nombre", "Mascota", "Asignatura");
            System.out.println("─".repeat(75));
            while (rs.next())
                System.out.printf("  %-25s %-15s %-30s%n",
                        rs.getString("nombre") + " " + rs.getString("apellido"),
                        rs.getString("mascota") != null ? rs.getString("mascota") : "—",
                        rs.getString("asignatura") != null ? rs.getString("asignatura") : "—");

        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void getEstudiantesSobreMediaEncantamientos() {
        String sql = """
                SELECT e.nombre, e.apellido, ea.calificacion
                FROM Estudiante e
                JOIN Estudiante_Asignatura ea ON e.id_estudiante = ea.id_estudiante
                JOIN Asignatura a ON ea.id_asignatura = a.id_asignatura
                WHERE LOWER(a.nombre) = 'encantamientos'
                  AND ea.calificacion > (
                      SELECT AVG(ea2.calificacion)
                      FROM Estudiante_Asignatura ea2
                      JOIN Asignatura a2 ON ea2.id_asignatura = a2.id_asignatura
                      WHERE LOWER(a2.nombre) = 'encantamientos'
                  )
                ORDER BY ea.calificacion DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n17. Estudiantes sobre la media en Encantamientos (subconsulta):");
            System.out.println("─".repeat(50));
            System.out.printf("  %-30s %-10s%n", "Nombre", "Nota");
            System.out.println("─".repeat(50));
            while (rs.next())
                System.out.printf("  %-30s %.1f%n",
                        rs.getString("nombre") + " " + rs.getString("apellido"),
                        rs.getDouble("calificacion"));

        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void getCasasConMediaCalificacionSuperior7() {
        String sql = """
                SELECT c.nombre AS casa,
                       ROUND(AVG(ea.calificacion)::numeric, 2) AS media
                FROM Casa c
                JOIN Estudiante e ON c.id_casa = e.id_casa
                JOIN Estudiante_Asignatura ea ON e.id_estudiante = ea.id_estudiante
                GROUP BY c.nombre
                HAVING AVG(ea.calificacion) > 7
                ORDER BY media DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n18. Casas con media de calificaciones > 7 (HAVING):");
            System.out.println("─".repeat(35));
            System.out.printf("  %-20s %-10s%n", "Casa", "Media");
            System.out.println("─".repeat(35));
            while (rs.next())
                System.out.printf("  %-20s %.2f%n",
                        rs.getString("casa"),
                        rs.getDouble("media"));

        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }
}
