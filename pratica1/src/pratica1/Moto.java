/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratica1;

/**
 *
 * @author Julien Gallego y Filipe
 */
public class Moto {
	//permite saber que id tiene que haber la nueva moto
	static int idIncrement = 0;
    
    int id;
    String nombre;
    int CC;
    int coste;
    Miembro miembro;
    
    Moto(String nombre,int CC,int coste, Miembro miembro){
        this.id = idIncrement;
        this.nombre = nombre;
        this.CC = CC;
        this.coste = coste;
        idIncrement = idIncrement + 1;
        this.miembro = miembro;
    }

	@Override
	public String toString() {
		return "Moto [id=" + id + ", nombre=" + nombre + ", CC=" + CC + ", coste=" + coste + ", miembro=" + miembro
				+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCC() {
		return CC;
	}

	public void setCC(int cC) {
		CC = cC;
	}

	public int getCoste() {
		return coste;
	}

	public void setCoste(int coste) {
		this.coste = coste;
	}

	public Miembro getMiembro() {
		return miembro;
	}

	public void setMiembro(Miembro miembro) {
		this.miembro = miembro;
	}
	
	
    
    
    
    
}


