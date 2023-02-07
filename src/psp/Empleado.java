package psp;

import java.io.Serializable;

/**
 * POJO Class of our table empleados.
 * an Employee will have the following fields: id, nombre, apellido.
 * Every field will have the getters and setters method except for id will
 * must not have the setter, since it's the primary key of each employee, 
 * we will not allow it to be changed.

 * @author Javi
 *
 */
public class Empleado implements Serializable {
    private int id;
    private String nombre;
    private String apellido;
    
    /**
     * Constructor of the class Empleado with the fields: nombre and apellido
     * @param nombre String name
     * @param apellido String surname
     */
    public Empleado(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    /**
     * Constructor of the class Empleado with all fields
     * @param id int id of the Employee
     * @param nombre String name of the Employee
     * @param apellido String surname of the Employee
     */
    public Empleado(int id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    /**
     * Return the id of the Employee
     * @return int - id
     */
    public int getId() {
        return id;
    }

    /**
     * Return the name of the Employee
     * @return String - name of the Employee
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el surname del empleado
     * @return String - surname of the Employee
     */
    public String getApellido() {
        return apellido;
    }
    
    /**
     * Set the name of the Employee
     * @param nombre - String of the Employee
     */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Set the surname of the Employee
	 * @param apellido - String of the Employee
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

    /**
     * Return a String with all fields of an Employee
     */
	@Override
	public String toString() {
		return id+", "+nombre+", "+apellido;
	}

}
