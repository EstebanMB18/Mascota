package dao;

import mundo.Mascota;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAOImplementation implements MascotaDAO {
    private final String driver = "org.apache.derby.jdbc.ClientDriver";
    private final String url = "jdbc:derby://localhost:1527/sample"; // Cambiar si tu BD es diferente
    private final String login = "app";
    private final String password = "app";

    public MascotaDAOImplementation() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró el driver JDBC.");
            e.printStackTrace();
        }
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, login, password);
    }

    @Override
    public void insertar(Mascota m) throws Exception {
        String sql = "INSERT INTO Mascota (nombre, edad, raza, dueño) VALUES (?, ?, ?, ?)";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getNombre());
            stmt.setInt(2, m.getEdad());
            stmt.setString(3, m.getRaza());
            stmt.setString(4, m.getDueño());
            stmt.executeUpdate();
        }
    }

    @Override
    public Mascota buscarPorNombre(String nombre) throws Exception {
        String sql = "SELECT * FROM Mascota WHERE nombre = ?";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Mascota(
                    rs.getString("nombre"),
                    rs.getString("raza"),
                    rs.getInt("edad"),
                    rs.getString("dueño")
                );
            }
        }
        return null;
    }

    @Override
    public List<Mascota> listar() throws Exception {
        List<Mascota> lista = new ArrayList<>();
        String sql = "SELECT * FROM Mascota";
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Mascota(
                    rs.getString("nombre"),
                    rs.getString("raza"),
                    rs.getInt("edad"),
                    rs.getString("dueño")
                ));
            }
        }
        return lista;
    }
@Override
public void eliminar(String nombre) throws Exception {
    String sql = "DELETE FROM Mascota WHERE nombre = ?";
    try (Connection conn = DriverManager.getConnection(url, login, password);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, nombre);
        stmt.executeUpdate();
    } catch (SQLException e) {
        throw new Exception("Error al eliminar mascota", e);
    }
}
}
