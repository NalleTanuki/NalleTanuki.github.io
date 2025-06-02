package actividad03;

/**
 * Actividad 03
 * 
 * 1. Crea una clase abstracta llamada Animal con los siguientes atributos:
 * 		a) Nombre, edad y raza.
 * 
 * 		b) Y los métodos abstractos:
 * 			- emitirSonido(): Metodo que debe ser implementado por las clases derivadas para representar el sonido que hace el animal.
 * 			- realizarTruco(): Metodo que debe ser implementado por las clases derivadas para representar un truco que puede realizar el animal.
 * 
 * 2. Crea dos clases que hereden de la clase Animal: Perro y Cotorra.
 * 		a) En cada clase hija (Perro y Cotorra), implementa los metodos abstractos emitirSonido() y realizarTruco().
 * 		Por ejemplo, un perro puede ladrar y hacer trucos como dar la pata,
 * 		mientras que una cotorra puede volar y realizar trucos como hablar.
 * 
 * 		b) La clase Perro contendra el atributo:
 * 			NumPelos.
 * 
 * 		c) La clase Cotorra contendra el atributo:
 * 			NumPlumas.
 * 
 * Crea una clase llamada Principal que contenga el metodo `main` donde instancies al menos un objeto de cada tipo (Perro y Cotorra)
 * y llames a los metodos emitirSonido() y realizarTruco().
 * 
 * 
 * @author Alexandra Carro
 * @date 29.01.2024
 * @version v1.0
 * DNI 54125991E
 * Modulo: Programacion
 * Actividad: 01
 * email alejandrat.carro@formacionchios.es
 */
public class Main {

	/**
	 * Metodo por donde empieza la aplicacion
	 * @param args
	 */
	public static void main(String[] args)
	{
		Perro perro1 = new Perro("Skadi", 1, "Pastor Alemán", 790000);
        Cotorra cotorra1 = new Cotorra("Lola", 11, "Aratinga solstitialis", 890);
        Perro perro2 = new Perro("Firulais", 6, "Beagle", 980000);

        perro1.emitirSonido();
        perro1.realizarTruco();
        
        cotorra1.emitirSonido();
        cotorra1.realizarTruco();
        
        perro2.emitirSonido();
        perro2.realizarTruco();
	}

}
