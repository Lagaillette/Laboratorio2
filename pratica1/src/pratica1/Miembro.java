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
public class Miembro {  
	//permite de saber que numero des socios tiene que haber el Miembro.
	static int idIncrement = 1;
	
    int numSocios;
    String nombre;
    int nMotos;
    int importe;
    Miembro(String nombre){
        this.numSocios = idIncrement;
        idIncrement += 1;
        this.nombre = nombre;
        this.nMotos = 0;
        this.importe = 0;
    }
    
	@Override
	public String toString() {
		return "Miembro [numSocios=" + numSocios + ", nombre=" + nombre + ", numero de motos=" + nMotos + ", importe=" + importe
				+ "]";
	}
	public int getNumSocios() {
		return numSocios;
	}
	public void setNumSocios(int numSocios) {
		this.numSocios = numSocios;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getnMotos() {
		return nMotos;
	}
	public void setnMotos(int nMotos) {
		this.nMotos = nMotos;
	}
	public int getImporte() {
		return importe;
	}
	public void setImporte(int importe) {
		this.importe = importe;
	}
    
    
    
}

