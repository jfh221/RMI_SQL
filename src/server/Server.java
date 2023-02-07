package server;

import psp.Empleado;
import psp.EmpleadoInterface;

import javax.swing.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Server class implements the EmployeeInterface and provides methods to
 * create, read, update and delete employee data using EmpleadoDAO class.
 * <p>
 * The class uses Java RMI (Remote Method Invocation) to bind a remote object's stub
 * in the registry and make the methods available for remote access.
 * @author Javifh
 */
public class Server implements EmpleadoInterface {

	// empDAO attribute to store an instance of EmpleadoDAO
	private final EmpleadoDAO empDAO;
	// port in which the server will be opened
	private static int port = 1099;

	/**
	 * Default constructor which initializes the empDAO attribute
	 */
	private Server() {
		empDAO = new EmpleadoDAO();
	}

	/**
	 * Main method which creates and exports a remote object,
	 * binds the remote object's stub in the registry and
	 * starts the server
	 *
	 * @param args - Command line arguments
	 */
	public static void main(String[] args) {
		boolean correctPort = false;
		while (!correctPort) {
			String portS = JOptionPane.showInputDialog("Indica el puerto al que desea conectarse");
			if (portS!=null) {
				if (portS.matches("[0-9]+")) {
					port = Integer.parseInt(portS);
					correctPort = true;
				} else {
					JOptionPane.showMessageDialog(null, "Puerto introducido no es un numero entero");
				}
			} else {
				JOptionPane.showMessageDialog(null, "No se ha introducido ningun puerto");
			}
		}
		try {
			// Create and export a remote object
			Server remoteObj = new Server();
			EmpleadoInterface stub = (EmpleadoInterface) UnicastRemoteObject.exportObject(remoteObj, 0);
			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.createRegistry(port);
			registry.bind(EmpleadoInterface.class.getSimpleName(), stub);
			JOptionPane.showMessageDialog(null, "Servidor creado correctamente con el puerto: "+port);
		} catch (RemoteException | AlreadyBoundException e) {
			JOptionPane.showMessageDialog(null, "ERROR: Port already in use");
			System.exit(-1);
		}
	}

	/**
	 * Creates a new employee by calling the create method of EmpleadoDAO
	 *
	 * @param emp - Empleado object to be created
	 * @throws RemoteException - if remote communication exception occurs
	 */
	@Override
	public void createEmp(Empleado emp) throws RemoteException {
		empDAO.create(emp);
	}

	/**
	 * Deletes an employee by calling the delete method of EmpleadoDAO
	 *
	 * @param empId - Employee id to be deleted
	 * @throws RemoteException - if remote communication exception occurs
	 */
	@Override
	public void deleteEmp(int empId) throws RemoteException {
		empDAO.delete(empId);
	}

	/**
	 * Updates an employee by calling the update method of EmpleadoDAO
	 *
	 * @param emp - Empleado object to be updated
	 * @throws RemoteException - if remote communication exception occurs
	 */
	@Override
	public void updateEmp(Empleado emp) throws RemoteException {
		empDAO.update(emp);
	}

	/**
	 * Reads all employees by calling the read method of EmpleadoDAO
	 *
	 * @return List of all employees
	 * @throws RemoteException - if remote communication exception occurs
	 */
	@Override
	public List<Empleado> readAllEmp() throws RemoteException {
		return empDAO.read();
	}

	/**
	 * Reads an employee by calling the read method of EmpleadoDAO
	 *
	 * @param empId - Employee id to be read
	 * @return Empleado object for the given employee id
	 * @throws RemoteException - if remote communication exception occurs
	 */
	@Override
	public Empleado readEmp(int empId) throws RemoteException {
		return empDAO.read(empId);
	}

}
