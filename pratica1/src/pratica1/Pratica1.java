/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratica1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


//el codigo esta escrito mas o menos en español para acostumbrarnos.
/**
 *
 * @author Julien Gallego y Filipe 
 */
public class Pratica1 {


	static ArrayList<Miembro> miembros = new ArrayList<Miembro>();

	static ArrayList<Moto> motos = new ArrayList<Moto>();

	static ArrayList<Cesion> historicaCesiones = new ArrayList<Cesion>();
	//importe inicial
	static int importeInicial = 0;
	public static void main(String[] args) {
		// TODO code application logic here	
		boolean end = false;

		int choice = 0;
		do{
			System.out.println("cual es el importe inicial? tiene que ser mas de 0");
			importeInicial = pedirEntero();
		}while(importeInicial <= 0);
		do{
			System.out.println("1. Registrar un nuevo miembro \n"
					+ "2. Registrar una nueva motocicleta \n"
					+ "3. Registrar una cesión \n"
					+ "4. Listar en pantalla los miembros con motos en posesión \n"
					+ "5. Listar todas las motos \n"
					+ "6. Mostrar las cesiones realizadas \n"
					+ "7. Incrementar otros gastos a una moto \n"
					+ "8. Eliminar un miembro \n"
					+ "9. Miembros con mas cesiones \n"
					+ "10. Salir del programa \n"
					);
			try {
				choice = pedirEntero();
				System.out.println(choice);
				switch(choice){
				case 1: registrarMiembro();
				break;
				case 2: registrarMoto();
				break;
				case 3: registrarUnaCesion();
				break;
				case 4: System.out.println(listaMiembrosMotos());
				break;
				case 5: listaMotos();
				break;
				case 6: System.out.println(listaCesiones());
				break;
				case 7: incrementarOtrosGastos();
				break;
				case 8: eliminarMiembro();
				break;
				case 9: miembrosConMasCesiones();
				break;
				case 10: escribirFicheroTexto();
						end = true;
				break;
				
				default : System.out.println("Porfavor, elige un numero valido");
				}

			} catch (Exception inputMismatchException) {
				System.out.println("Valor no funciona");
			}
		}while(!end);
	}

	/**
     *registrar un miembro.
	 */
	public static void registrarMiembro(){
		System.out.println("Cual es el nombre del miembro");
		String nombre = pedirPalabra();
		Miembro miembro = new Miembro(nombre);
		miembros.add(miembro);
		listaMiembros();
	}

	/**
	 * registrar una moto.
	 */
	public static void registrarMoto(){
		if(miembros.isEmpty() ){
			System.out.println("No puedes añadir una moto si no hay miembro");
		}
		else{
			String nombre;
			do{
				System.out.println("Escribir el nombre de la moto");
				nombre = pedirPalabra();
			}while(nombre.length() == 0);
			
			System.out.println("Escribir el CC de la moto");
			int CC = pedirEntero();
			System.out.println("Escribir el coste de la moto");
			int coste = pedirEntero();
			Miembro miembro = null;
			int numeroSocios;
			

			System.out.println("Escribir los otros gastos");
			int otrosGastos = pedirEnteroPositivo();
			
			do{
				System.out.println("Escribir el numero de socios de el que va a tener la moto");
				listaMiembros();
				numeroSocios = pedirEntero();
			}while(!existeMiembro(numeroSocios));
			
			
			miembro = getMiembroByNumSocios(numeroSocios);
			Moto moto = new Moto(nombre, CC, coste, miembro, otrosGastos);
			//si podemos añadir la moto a un miembro, entonces añadimos
			if(añadirMotoAMiembro(moto, miembro)){
				motos.add(moto);
				System.out.println("moto bien registrada");
			}
			else{
				System.out.println("por favor, empiezas otra vez");
			}		
		}
	}


	/**
	 * Registrar una cesion.
	 */
	public static void registrarUnaCesion(){
		if(motos.size() == 0 || miembros.size() < 2 ){
			System.out.println("No Hay miembros o motos necesarios para registrar una cesion");
		}else{
			boolean valorIncorrecto = true;
			Miembro miembro1 = null;
			Miembro miembro2 = null;
			Moto moto = null;
			int id;
			int numSocios1;
			int numSocios2;
			System.out.println(listaMiembrosMotos());
			do{
				System.out.println("cual es el id de la moto que quieres que cambia de miembro");
				id = pedirEntero();
			}while(!existeMoto(id));
			
			moto = getMotoById(id);
			
			registrarCesionDeUnaMoto(moto);
		}
		

	}
	
	public static void registrarCesionDeUnaMoto(Moto moto){
		//el que va a tener la moto
		Miembro miembro1;
		//el que va a dejar la moto
		Miembro miembro2;
		listaMiembros();
		int numSocios1;
		do{
			System.out.println("cual es el id del miembro que va a haber la moto");
			numSocios1 = pedirEntero();
		}while(!existeMiembro(numSocios1));
		miembro1 = getMiembroByNumSocios(numSocios1);
		
		miembro2 = moto.getMiembro();
		
		if (miembro1 == miembro2){
			System.out.println("No puedes hacer una cesion para el mismo miembro");
		}else{
			//si podemos anadir la moto al nuevo miembro, entonces lo hacemos
			if(añadirMotoAMiembro(moto, miembro1)){
				moto.setMiembro(miembro1);
				// quitamos la moto al miembro que deja la moto
				quitarMotoAMiembro(moto, miembro2);
				Cesion cesion = new Cesion(moto, miembro1, miembro2);
				historicaCesiones.add(cesion);
			}
		}
		
	}

	/**
	 * listar los miembros y las motos que tienen.
	 * @return String la lista de los miembros y las motos que tienen.
	 */
	public static String listaMiembrosMotos(){
		String resultado = "";
		if(miembros.isEmpty()){
			resultado = "no hay miembros, perdonna";
		}
		else{
			// inicialización de las variables
			Moto moto;
			Miembro miembro;
			Iterator itMiembros = miembros.iterator();
			
			while(itMiembros.hasNext()){
				miembro = (Miembro) itMiembros.next();
				//escribimos los miembros en el resultado
				resultado += miembro.toString() + "\n";
				Iterator itMotos = motos.iterator();
				while(itMotos.hasNext()){
					moto = (Moto) itMotos.next();
					if(moto.getMiembro() == miembro){
						//escribimos las motos en el resultado
						resultado += "   " + moto.toString() + "\n";
					}

				}
			}
		}
		return resultado;

	}
	
	/**
	 * Listar las motos.
	 */
	public static void listaMotos(){
		Iterator it = motos.iterator();
		while(it.hasNext()){
			System.out.println(it.next().toString());
		}
	}
	
	/**
	 * Listar los miembros.
	 */
	public static void listaMiembros(){
		Iterator it = miembros.iterator();
		while(it.hasNext()){
			System.out.println(it.next().toString());
		}
	}
	
	/**
	 * Listar las cesiones.
	 * @return String la lista de la cesiones.
	 */
	public static String listaCesiones(){
		String resultado = "";
		Iterator it = historicaCesiones.iterator();
		while(it.hasNext()){
			resultado += it.next().toString() + "\n";
		}
		return resultado;
	}

	/**
	 * Pedir un entero.
	 * @return el entero que ha puesto el usuario.
	 */
	public static int pedirEntero(){

		// inicialización de las variables
		Scanner input = new Scanner(System.in);
		int resultado = 0;
		boolean valorIncorrecto = true;

		do{
			try{
				resultado = input.nextInt();
				valorIncorrecto = false;
			}
			catch (Exception a){
				System.out.println("Valor no funciona. Tienes que poner un numero. Intentar otra vez");
				input.nextLine();
			}
		}while(valorIncorrecto);
		return resultado;
	}
	
	/**
	 * Pedir un entero que es mas grande o igual a 0.
	 * @return el entero que ha puesto el usuario mas grande o igual a 0.
	 */
	public static int pedirEnteroPositivo(){

		// inicialización de las variables
		Scanner input = new Scanner(System.in);
		int resultado = 0;
		boolean valorIncorrecto = true;

		do{
			try{
				resultado = input.nextInt();
				valorIncorrecto = false;
			}
			catch (Exception a){
				System.out.println("Valor no funciona. Tienes que poner un numero. Intentar otra vez");
				input.nextLine();
			}
		}while(valorIncorrecto || resultado < 0);
		return resultado;
	}

	/**
	 * Pedir una palabra.
	 * @return la palabra pedida por el usuario.
	 */
	public static String pedirPalabra(){
		
		// inicialización de las variables
		Scanner input = new Scanner(System.in);
		String resultado = null;
		boolean valorIncorrecto = true;

		do{
			try{
				resultado = input.next();
				valorIncorrecto = false;
			}
			catch (Exception a){
				System.out.println("Valor no funciona. Tienes que poner un numero. Intentar otra vez");
				input.nextLine();
			}
		}while(valorIncorrecto);
		return resultado;
	}

	/**
	 * Saber si un numero de socios coresponde con un miembro registrado.
	 * @param numSocios el numero de socios del miembro que buscamos.
	 * @return true si el miembro existe, false sino.
	 */
	public static boolean existeMiembro(int numSocios){
		boolean existe = false;
		Miembro miembro;
		Iterator<Miembro> it = miembros.iterator();
		while(it.hasNext() && existe==false){
			miembro = (Miembro) it.next();
			if(miembro.getNumSocios() == numSocios){
				existe = true;
			}
		}
		if(!existe){
			System.out.println("este Miembro no existe");
		}
		return existe;
	}
	
	/**
	 * Saber si un id de una moto coresponde con una moto registrada.
	 * @param numSocios el id de la moto que buscamos.
	 * @return true si la moto existe, false sino.
	 */
	public static boolean existeMoto(int id){
		boolean existe = false;
		Moto moto;
		Iterator it = motos.iterator();
		while(it.hasNext() && existe!=true){
			moto = (Moto) it.next();
			if(moto.getId() == id){
				existe = true;
			}
		}
		if(!existe){
			System.out.println("esta moto no existe");
		}
		return existe;
	}
	
	/**
	 * Obtener un miembro gracias a su numero de socios.
	 * @param numSocios el numero de socios del miembro que buscamos.
	 * @return el miembro que corresponde al numero de socios, y si ningun miembro corresponde, return un miembro null.
	 */
	public static Miembro getMiembroByNumSocios(int numSocios){
		boolean encontrado = false;
		Miembro miembro = null;
		Iterator it = miembros.iterator();
		while(it.hasNext() && !encontrado){
			miembro = (Miembro) it.next();
			if(miembro.getNumSocios() == numSocios){
				encontrado = true;
			}
		}
		return miembro;

	}
	/**
	 * permite coger todas las cesiones adonde este miembro ha recibido una moto
	 * @param miembro
	 * @return
	 */
	public static ArrayList<Cesion> getCesionesDeUnMiembro(Miembro miembro){
		ArrayList<Cesion> resultado = new ArrayList<Cesion>();
		
		Cesion cesion;
		Iterator itCesion = historicaCesiones.iterator();
		while(itCesion.hasNext()){
			cesion = (Cesion) itCesion.next();
			if(cesion.getMiembroNuevo() == miembro){
				resultado.add(cesion);
			}
		}
		return resultado;
	}
	
	/**
	 * Obtener una moto gracias a su Id
	 * @param id el id de la moto que buscamos
	 * @return la moto que corresponde al id, y si ninguna moto corresponde, return una moto null.
	 */
	public static Moto getMotoById(int id){
		boolean encontrado = false;
		Moto moto = null;
		Iterator it = motos.iterator();
		while(it.hasNext() && !encontrado){
			moto = (Moto) it.next();
			if(moto.getId() == id){
				encontrado = true;
			}
		}
		return moto;
	}
	
	/**
	 * Permite hacer las modificaciones del importe y del numero de motos des miembre a quien añadimos la moto
	 * @param moto la moto que estamos añadiendo
	 * @param miembro el miembro a quien añadimos la moto
	 * @return true si es posible añadir la moto al miembro, false si no
	 */
	public static boolean añadirMotoAMiembro(Moto moto, Miembro miembro){
		boolean añadido = false;
		int importe = miembro.importe + moto.coste;
		if(importe > importeInicial){
			System.out.println("Este miembro no puede coger esta moto. Su importe es demasiado grande");
		}else{
			int indexMiembro = miembros.indexOf(miembro);
			miembro.importe += moto.coste;
			miembro.nMotos += 1;
			miembros.set(indexMiembro, miembro);			
			añadido = true;	
		}
		return añadido;	
	}
	

	/**
	 * Permite hacer las modificaciones del importe y del numero de motos des miembre a quien quitamos la moto
	 * @param moto
	 * @param miembro
	 */
	public static void quitarMotoAMiembro(Moto moto, Miembro miembro){
		int index = miembros.indexOf(miembro);
		miembro.importe -= moto.coste;
		miembro.nMotos -= 1;
		miembros.set(index, miembro);
	}
	
	/**
	 * permite incrementar otros gastos de una moto
	 */
	public static void incrementarOtrosGastos(){
		listaMotos();
		System.out.println("que es el id de la moto que quiere incrementar los gastos");
		int idMoto;
		do{
			idMoto = pedirEntero();
		}while(!existeMoto(idMoto));
		
		System.out.println("Cuales son los nuevos gastos a añadir?");
		int otrosGastos = pedirEnteroPositivo();
		
		Moto moto = getMotoById(idMoto);
		moto.setOtrosGastos(moto.getOtrosGastos() + otrosGastos);
		
		int index = motos.indexOf(moto);
		motos.set(index, moto);
		
		System.out.println("otros gastos bien añadidos");
	}
	
	/**
	 * elimina el miembro que vas a entrar el numero de socios y todas las cesiones a donde el esta
	 */
	public static void eliminarMiembro(){
		Moto moto;
		int numSocios;
		listaMiembros();
		System.out.println("Cual es el miembro que quieres eliminar?");
		do{
			numSocios = pedirEntero();
		}while(!existeMiembro(numSocios));
		Miembro miembro = getMiembroByNumSocios(numSocios);
		ArrayList<Moto> motosACambiar = cogerMotoDeUnMiembro(numSocios);
		Iterator itMotos = motosACambiar.iterator();
		while(itMotos.hasNext()){
			moto = (Moto) itMotos.next();
			System.out.println("Tenemos que cambiar el miembro que tiene la moto : " + moto.toString() + "/n");
			while(moto.getMiembro() == miembro){
				registrarCesionDeUnaMoto(moto);
			}	
		}
		eliminarCesionesDeMiembros(miembro);
		miembros.remove(miembro);
		
		
	}
	
	public static void eliminarCesionesDeMiembros(Miembro miembro){
		Iterator itCesiones = historicaCesiones.iterator();
		while(itCesiones.hasNext()){
			Cesion cesion = (Cesion) itCesiones.next();
			if(cesion.miembroNuevo == miembro || cesion.miembroAntiguo == miembro){
				itCesiones.remove();
			}
			
		}
	}
	
	/**
	 * 
	 * @param numSocios
	 * @return
	 */
	public static ArrayList<Moto> cogerMotoDeUnMiembro(int numSocios){
		ArrayList<Moto> motosDelMiembro = new ArrayList<Moto>();
		Moto moto;
		Miembro miembro = getMiembroByNumSocios(numSocios);
		Iterator itMotos = motos.iterator();
		while(itMotos.hasNext()){
			moto = (Moto) itMotos.next();
			if(moto.getMiembro() == miembro){
				motosDelMiembro.add(moto);
			}

		} 
		return motosDelMiembro;
	}
	
	/**
	 * 
	 */
	public static void miembrosConMasCesiones(){
		if(miembros.size() <= 1){
			if(miembros.size() == 0){
				System.out.println("No hay miembros");
			}
			else{
				System.out.println("El unico miembro es " + miembros.get(0).toString());
				System.out.println("y sus motos son ");
				listaMotos();
			}
		}else{
			if(historicaCesiones.size() == 0){
				System.out.println("No hay cesiones registradas");
			}else{
				//Inicializacion del array ocurrencia, que va summir cada vez que un miembre ha recibido una moto
				int ocurrencia[] = new int[miembros.size()];
				int index;
				Miembro miembro;
				Cesion cesion;
				Iterator itCesiones = historicaCesiones.iterator();
				while(itCesiones.hasNext()){
					cesion = (Cesion) itCesiones.next();
					//recuperamos el miembro que va tener la moto
					miembro = cesion.getMiembroNuevo();
					//recuperamos el index del miembro dentro de la lista de miembros
					index = miembros.indexOf(miembro);
					//incrementamos el numero de vez que aparece el miembro como miembro antiguo
					ocurrencia[index] += 1;
				}
				//lista de los miembros que tienen mas cesiones
				ArrayList<Miembro> miembrosConMasCesiones = new ArrayList<Miembro>();
				// el numero maximo de ocurrencia de cada miembro
				int maxOcurrencia = 0;
				for(int i=0; i<miembros.size(); i++){
					if(ocurrencia[i] > maxOcurrencia){
						miembrosConMasCesiones.clear();
						miembrosConMasCesiones.add(miembros.get(i));
						maxOcurrencia = ocurrencia[i];
					}else{
						if(ocurrencia[i] == maxOcurrencia){
							miembrosConMasCesiones.add(miembros.get(i));	
						}
					}
				}
				Moto moto;
				ArrayList<Cesion> cesionesDelMiembro = new ArrayList<Cesion>();
				Iterator itMiembros = miembrosConMasCesiones.iterator();
				while (itMiembros.hasNext()){
					miembro = (Miembro) itMiembros.next();
					System.out.println(miembro.toString());
					System.out.println("ha recibido");
					cesionesDelMiembro = getCesionesDeUnMiembro(miembro);
					Iterator it = cesionesDelMiembro.iterator();
					while(it.hasNext()){
						cesion =  (Cesion) it.next();
						System.out.println("  " + cesion.getMoto().toString());		
					}
				}
			}
		}
			
		
	}
	
	/**
	 * Escribe los miembros con las motos y las cesiones dentro de un fichero
	 */
	public static void escribirFicheroTexto(){
	    try {
	    	System.out.println("Cual es el nombre del fichero");
	    	String nombre = pedirPalabra();
	    	File f = new File(nombre + ".txt");
			FileWriter fw = new FileWriter (f);
			if(miembros.isEmpty()){
				fw.write("no hay miembros, perdonna");
				System.out.println("no hay miembros, perdonna");
			}
			else{
				fw.write(listaMiembrosMotos());	
				fw.write("\n");
				fw.write(listaCesiones());
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
