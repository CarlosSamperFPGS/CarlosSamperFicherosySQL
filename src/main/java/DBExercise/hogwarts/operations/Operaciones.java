package DBExercise.hogwarts.operations;

import DBExercise.hogwarts.config.DatabaseConnection;
import DBExercise.hogwarts.modelo.Asignatura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Operaciones {
    public List<Asignatura> getAllAsignaturas() {
        List<Asignatura> lista = new ArrayList<>();
        String sql = "SELECT id_asignatura, nombre, aula, obligatoria FROM asignatura ORDER BY id_asignatura";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Asignatura(
                        rs.getInt("id_asignatura"),
                        rs.getString("nombre"),
                        rs.getString("aula"),
                        rs.getBoolean("obligatoria")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar asignaturas: " + e.getMessage());
        }
        return lista;
    }

    public List<String> getEstudiantesPorCasa(String nombreCasa) {
        List<String> estudiantes = new ArrayList<>();

        String sql = """
                SELECT e.nombre, e.apellido
                FROM Estudiante e
                JOIN Casa c ON e.id_casa = c.id_casa
                WHERE LOWER(c.nombre) = LOWER(?)
                ORDER BY e.apellido, e.nombre
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreCasa);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                estudiantes.add(rs.getString("nombre") + " " + rs.getString("apellido"));
            }

            if (estudiantes.isEmpty()) {
                System.out.println("No se encontró la casa: " + nombreCasa);
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar estudiantes por casa: " + e.getMessage());
        }
        return estudiantes;
    }

    public void getMascotaDeEstudiante(String nombre, String apellido) {

        String sql = """
                SELECT m.nombre AS mascota, m.especie
                FROM Mascota m
                JOIN Estudiante e ON m.id_estudiante = e.id_estudiante
                WHERE LOWER(e.nombre) = LOWER(?)
                  AND LOWER(e.apellido) = LOWER(?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ResultSet rs = ps.executeQuery();

            boolean tieneMascota = false;
            System.out.println("\n🐾 Mascotas de " + nombre + " " + apellido + ":");
            System.out.println("─".repeat(35));

            while (rs.next()) {
                tieneMascota = true;
                System.out.printf("  %-15s (%-10s)%n",
                        rs.getString("mascota"),
                        rs.getString("especie"));
            }

            if (!tieneMascota) {
                System.out.println("  Sin mascotas registradas.");
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar mascota: " + e.getMessage());
        }
    }

    public void getNumEstudiantesPorCasa() {

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

            System.out.println("\nNúmero de estudiantes por casa:");
            System.out.println("─".repeat(35));
            System.out.printf("  %-20s %s%n", "Casa", "Estudiantes");
            System.out.println("─".repeat(35));

            while (rs.next()) {
                System.out.printf("  %-20s %d%n",
                        rs.getString("casa"),
                        rs.getInt("total"));
            }

        } catch (SQLException e) {
            System.err.println("Error al contar estudiantes por casa: " + e.getMessage());
        }
    }

    public void insertarAsignatura(String nombre, String aula, boolean obligatoria) {

        String sql = "INSERT INTO Asignatura (nombre, aula, obligatoria) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nombre);
            ps.setString(2, aula);
            ps.setBoolean(3, obligatoria);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    System.out.println("\nAsignatura insertada correctamente.");
                    System.out.println("   ID generado : " + keys.getInt(1));
                    System.out.println("   Nombre      : " + nombre);
                    System.out.println("   Aula        : " + aula);
                    System.out.println("   Obligatoria : " + (obligatoria ? "Sí" : "No"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar asignatura: " + e.getMessage());
        }
    }

    public void modificarAulaAsignatura(int idAsignatura, String nuevaAula) {

        String sql = "UPDATE Asignatura SET aula = ? WHERE id_asignatura = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevaAula);
            ps.setInt(2, idAsignatura);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("\nAula actualizada correctamente.");
                System.out.println("   ID asignatura : " + idAsignatura);
                System.out.println("   Nueva aula    : " + nuevaAula);
            } else {
                System.out.println("No se encontró asignatura con ID: " + idAsignatura);
            }

        } catch (SQLException e) {
            System.err.println("Error al modificar el aula: " + e.getMessage());
        }
    }

    public void eliminarAsignatura(int idAsignatura) {

        String sql = "DELETE FROM Asignatura WHERE id_asignatura = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAsignatura);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("\nAsignatura con ID " + idAsignatura + " eliminada correctamente.");
            } else {
                System.out.println("No se encontró asignatura con ID: " + idAsignatura);
            }

        } catch (SQLException e) {
            // La BD tiene ON DELETE RESTRICT en Estudiante_Asignatura
            System.err.println("Error al eliminar asignatura: " + e.getMessage());
            System.err.println("Puede que tenga estudiantes asignados (RESTRICT).");
        }
    }
}