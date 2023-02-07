package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import psp.Empleado;

/**
 * EmpleadoDAO class will interact with the table empleados of the database empleadosbd
 * allowing us to connect, disconnect from the database and
 * execute query's in that table that will allow us to:
 * - create Employees
 * - read a specific Employee
 * - read all Employees
 * - update an Employee's data
 * - delete an Employee
 * @author Javi
 *
 */
public class EmpleadoDAO {
    private Connection conexion;

    // Our Database link
    private final String URL = "jdbc:mysql://localhost:3306/empleadosbd";
    // The user of our database
    private final String USER = "root";
    // And the password of the user
    private final String PASSWORD = "dam1";

    /**
     * EmpleadoDAO constructor.
     * It will connect to the database on construction
     */
    public EmpleadoDAO() {
        conectar();
    }

    /**
     * Method responsible for establishing the connection to the database
     */
    public void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new employee record.
     *
     * @param empleado the employee object to be added to the database
     */
    public void create(Empleado empleado) {
        try {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO empleados (nombre, apellido) VALUES (?, ?)");
            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApellido());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a single employee record from the database.
     *
     * @param id the id of the employee to be retrieved from the database
     * @return the employee object with the specified id
     */
    public Empleado read(int id) {
        Empleado empleado = null;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM empleados WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                empleado = new Empleado(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleado;
    }

    /**
     * Reads all employee records from the database.
     *
     * @return a list of all employees in the database
     */
    public List<Empleado> read() {
        List<Empleado> empleados = new ArrayList<>();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM empleados");
            while (rs.next()) {
                empleados.add(new Empleado(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    /**
     * Updates an existing employee record.
     *
     * @param empleado the updated employee object to be saved to the database
     */
    public void update(Empleado empleado) {
        try {
            PreparedStatement ps = conexion.prepareStatement("UPDATE empleados SET nombre = ?, apellido = ? WHERE id = ?");
            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApellido());
            ps.setInt(3, empleado.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an employee record.
     *
     * @param id the id of the employee to be deleted from the database
     */
    public void delete(int id) {
        try {
            PreparedStatement ps = conexion.prepareStatement("DELETE FROM empleados WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}