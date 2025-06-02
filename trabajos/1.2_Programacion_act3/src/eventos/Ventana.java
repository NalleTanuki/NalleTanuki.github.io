package eventos;

import javax.swing.JFrame;

public class Ventana extends JFrame{

	public Ventana() {

		setTitle("Examen");
		setResizable(false);

		setBounds(700, 100, 320, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Evento1 evento1 = new Evento1(); 
		add(evento1);

		setVisible(true);


	}


}
