package ar.edu.ubp.das.bean;

public class InfoAsistenciaBean {

	private String 	idServicio;
	private int 	dni;
	private String  dato;
	private UbicacionBean ubicacion;
	
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public String getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}

	public String getDato() {
		return dato;
	}
	public void setDato(String dato) {
		this.dato = dato;
	}
	public UbicacionBean getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(UbicacionBean ubicacion) {
		this.ubicacion = ubicacion;
	}	
}
