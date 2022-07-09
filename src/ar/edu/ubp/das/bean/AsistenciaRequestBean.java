package ar.edu.ubp.das.bean;

public class AsistenciaRequestBean {
	
	private String token;
	private String fecha;
	private InfoAsistenciaBean informacion;
	private String idSolicitud;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public InfoAsistenciaBean getInformacion() {
		return informacion;
	}
	public void setInformacion(InfoAsistenciaBean informacion) {
		this.informacion = informacion;
	}
	public String getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	
	
}