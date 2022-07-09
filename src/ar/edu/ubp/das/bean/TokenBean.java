package ar.edu.ubp.das.bean;

import java.sql.Timestamp;

public class TokenBean {
	
	private int 	id;
	private String 	idEntidad;
	private String 	token;
	private Timestamp 	fechaCreacion;
	private Timestamp 	fechaExpiracion;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getIdEntidad() {
		return idEntidad;
	}
	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Timestamp getFechaExpiracion() {
		return fechaExpiracion;
	}
	public void setFechaExpiracion(Timestamp fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}
	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}