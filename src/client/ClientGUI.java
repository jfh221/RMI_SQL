package client;

import com.formdev.flatlaf.FlatDarkLaf;
import psp.Empleado;
import psp.EmpleadoInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 *
 * Graphical User Interface (GUI)
 * <p>
 * ClientGUI class will allow our Client to interact with our Server's database utilizing the EmpleadoInterface methods.
 * <br><br>
 * Makes use of the LocateRegistry class in Java and its <code>getRegistry()</code> method
 * to get a reference to the object registry on the remote host with port 1099.
 * It then uses the <code>lookup()</code> method to get a reference to a remote object
 * that implements the EmployeeInterface interface and assigns it to the variable "stub".
 * <br>
 * By doing that now we will call the corresponding methods of our stub for each button.
 * With this our client will be able to create, update, remove, read and readAll Employees from our database.
 * @author Javi
 *
 */
public class ClientGUI extends JFrame {

	private JTextField textFieldNombre;
	private JTextField textFieldApellido;
	private JTextArea textAreaEmpleados;

	// Registry and stub
	Registry registry;
	EmpleadoInterface stub;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				String portS = JOptionPane.showInputDialog("Indica el puerto al que desea conectarse");
				if (portS!=null) {
					if (portS.matches("[0-9]+")) {
						int port = Integer.parseInt(portS);
						ClientGUI frame = new ClientGUI(port);
						frame.setVisible(true);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * GUI constructor, will create the JFrame, initialize the registry and the stub.
	 * It will call <code>initialize()</code> to load the graphical swing components,
	 * and it will finally call <code>cargarEmpleados()</code> to from the start show in our
	 * textArea all Employees from our database.
	 */
	public ClientGUI(int port) {
		try {
			/*
			 * This code makes use of the LocateRegistry class in Java and its getRegistry method
			 * to get a reference to the object registry on the remote host with port 1099.
			 * Then, it uses the lookup method to get a reference to a remote object that implements the EmployeeInterface interface
			 * and assigns it to the variable "stub".
			 * This remote object is accessed through the "stub" and can be used to invoke remote methods on the object on the remote host.
			 */
			registry = LocateRegistry.getRegistry(port);
			// Name in lookup method will be the same as the EmpleadoInterface simpleName
			stub = (EmpleadoInterface) registry.lookup(EmpleadoInterface.class.getSimpleName());
		} catch(ConnectException ce) {
			// Server probably is not started
			JOptionPane.showMessageDialog(null, "Server disconnected. Connection refused");
			System.exit(-1);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		initizalize();
		loadEmployees();
	}

	/**
	 * It will initialize all the graphical components of our interface,
	 * give to the buttons the instructions to be executed when they are clicked.
	 * And assign that when the program is closing it disconnects from the database.
	 */
	private void initizalize() {
		FlatDarkLaf.setup();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 825, 540);
		// Swing Objects
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelRegistro = new JPanel();
		panelRegistro.setLayout(null);
		panelRegistro.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Crear / Modificar : Empleados"
				,TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		panelRegistro.setBounds(10, 11, 420, 200);
		contentPane.add(panelRegistro);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Verdana", Font.BOLD, 20));
		lblNombre.setBounds(25, 50, 95, 30);
		panelRegistro.add(lblNombre);

		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setFont(new Font("Verdana", Font.BOLD, 20));
		lblApellido.setBounds(25, 120, 95, 30);
		panelRegistro.add(lblApellido);

		textFieldNombre = new JTextField();
		textFieldNombre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(135, 39, 255, 50);
		panelRegistro.add(textFieldNombre);

		textFieldApellido = new JTextField();
		textFieldApellido.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldApellido.setColumns(10);
		textFieldApellido.setBounds(135, 112, 255, 50);
		panelRegistro.add(textFieldApellido);

		/*
		 * Create Employee button
		 */
		JButton btnCrearEmpleado = new JButton("Crear Empleado");
		btnCrearEmpleado.addActionListener(e -> {
			if (!"".equals(textFieldNombre.getText()) && !"".equals(textFieldApellido.getText())) {
				Empleado emp = new Empleado(textFieldNombre.getText(), textFieldApellido.getText());
				try {
					stub.createEmp(emp);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				clearTextFields();
				loadEmployees();
			} else {
				JOptionPane.showMessageDialog(null, "Rellene los campos apellido y nombre para introducir un empleado");
			}
		});
		btnCrearEmpleado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCrearEmpleado.setBounds(10, 222, 200, 57);
		contentPane.add(btnCrearEmpleado);

		/*
		 * Update Employee method
		 */
		JButton btnModificarEmpleado = new JButton("Modificar Empleado");
		btnModificarEmpleado.addActionListener(e -> {
			String idS = JOptionPane.showInputDialog("Indica el id del empleado a modificar");
			if (idS!=null) {
				if (idS.matches("[0-9]+")) {
					if (!"".equals(textFieldNombre.getText()) || !"".equals(textFieldApellido.getText())) {
						int id = Integer.parseInt(idS);
						try {
							Empleado emp = stub.readEmp(id);
							if (emp==null) {
								JOptionPane.showMessageDialog(null, "No existe un empleado con ese id");
							} else {
								if(!"".equals(textFieldNombre.getText())){
									emp.setNombre(textFieldNombre.getText());
								}
								if(!"".equals(textFieldApellido.getText())){
									emp.setApellido(textFieldApellido.getText());
								}
								stub.updateEmp(emp);
							}
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						clearTextFields();
						loadEmployees();
					} else {
						JOptionPane.showMessageDialog(null, "Cambie los campos apellido o nombre para modificar un empleado");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Id introducido no es un numero entero");
				}
			}
		});
		btnModificarEmpleado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnModificarEmpleado.setBounds(220, 222, 210, 57);
		contentPane.add(btnModificarEmpleado);

		/*
		 * Delete Employee button
		 */
		JButton btnEliminarEmpleado = new JButton("Eliminar Empleado");
		btnEliminarEmpleado.addActionListener(e -> {
			String idS = JOptionPane.showInputDialog("Indica el id del empleado a eliminar");
			if (idS!=null) {
				if (idS.matches("[0-9]+")) {
					int id = Integer.parseInt(idS);
					try {
						stub.deleteEmp(id);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					loadEmployees();
				} else {
					JOptionPane.showMessageDialog(null, "Id introducido no es un numero entero");
				}
			}
		});
		btnEliminarEmpleado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnEliminarEmpleado.setBounds(10, 290, 200, 57);
		contentPane.add(btnEliminarEmpleado);

		/*
		 * Read Employee button
		 */
		JButton btnLeerEmp = new JButton("Leer Empleado");
		btnLeerEmp.addActionListener(e -> {
			String idS = JOptionPane.showInputDialog("Indica el id del empleado a eliminar");
			if (idS!=null) {
				if (idS.matches("[0-9]+")) {
					int id = Integer.parseInt(idS);
					textAreaEmpleados.setText("");
					Empleado emp;
					try {
						emp = stub.readEmp(id);
						if (emp!=null) {
							textAreaEmpleados.setText(emp.toString());
						} else {
							JOptionPane.showMessageDialog(null, "No existe un empleado con este id");
						}
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Id introducido no es un numero entero");
				}
			}
		});
		btnLeerEmp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLeerEmp.setBounds(220, 290, 210, 57);
		contentPane.add(btnLeerEmp);

		/*
		 * Read all Employees button
		 */
		JButton btnLeerTodosEmp = new JButton("Leer todos los Empleados");
		btnLeerTodosEmp.addActionListener(e -> loadEmployees());
		btnLeerTodosEmp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLeerTodosEmp.setBounds(10, 358, 420, 57);
		contentPane.add(btnLeerTodosEmp);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(440, 11, 347, 479);
		contentPane.add(scrollPane);

		textAreaEmpleados = new JTextArea();
		scrollPane.setViewportView(textAreaEmpleados);
		textAreaEmpleados.setFont(new Font("Monospaced", Font.PLAIN, 16));
	}

	/**
	 * Loop through received list in the stub method.
	 * And for each Employee append its data to our textArea
	 */
	private void loadEmployees() {
		textAreaEmpleados.setText("");
		List<Empleado> empList;
		try {
			empList = stub.readAllEmp();
			for (Empleado emp : empList) {
				textAreaEmpleados.append(emp.toString() + "\n");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Void method that will clear the TextFields after an update or create button has been pressed
	 */
	private void clearTextFields() {
		textFieldNombre.setText("");
		textFieldApellido.setText("");
	}
}

