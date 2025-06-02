package eventos;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Evento1 extends JPanel{

	private JLabel jlNombre;
	private JTextField jtNombre;
	private JButton btnSalir;

	public Evento1() {

		setLayout(null);
		
		jlNombre = new JLabel("Introduce tu nombre: ");
		jlNombre.setBounds(5, 10, 140, 30 );
		add(jlNombre);
		
		jtNombre = new JTextField();
		jtNombre.setBounds(155, 10, 120, 30 );
		add(jtNombre);
		
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(20, 180, 80, 20 );
		add(btnSalir);

		
	}


}
