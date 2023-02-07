package psp;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * EmpleadoInterface is an interface that defines the API for remote operations on employee data.
 * It extends java.rmi.Remote and declares methods for creating, deleting, updating, 
 * reading all and reading a single employee record.
 *
 * @author Javifh
 */
public interface EmpleadoInterface extends Remote {
	/**
	 * Creates a new employee record.
	 *
	 * @param emp the employee object to be added to the database
	 * @throws RemoteException if there is an issue with remote communication
	 */
	void createEmp(Empleado emp) throws RemoteException;
	/**
	 * Deletes an employee record.
	 *
	 * @param empId the id of the employee to be deleted from the database
	 * @throws RemoteException if there is an issue with remote communication
	 */
	void deleteEmp(int empId) throws RemoteException;
	/**
	 * Updates an existing employee record.
	 *
	 * @param emp the updated employee object to be saved to the database
	 * @throws RemoteException if there is an issue with remote communication
	 */
	void updateEmp(Empleado emp) throws RemoteException;
	/**
	 * Reads all employee records from the database.
	 *
	 * @return a list of all employees in the database
	 * @throws RemoteException if there is an issue with remote communication
	 */
	List<Empleado> readAllEmp() throws RemoteException;
	/**
	 * Reads a single employee record from the database.
	 *
	 * @param empId the id of the employee to be retrieved from the database
	 * @return the employee object with the specified id
	 * @throws RemoteException if there is an issue with remote communication
	 */
	Empleado readEmp(int empId) throws RemoteException;
}
