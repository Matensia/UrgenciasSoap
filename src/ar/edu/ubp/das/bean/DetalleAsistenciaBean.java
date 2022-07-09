package ar.edu.ubp.das.bean;


public class DetalleAsistenciaBean {
	
	private int 	id;
	private String 	idAsistencia;
	private String 	tipoDato;
	private String 	dato;
	private String 	fecha;
	private String  creadoPor;
	private Boolean asistenciaFinalizada;

	public String getCreadoPor() {
		return creadoPor;
	}
	public void setCreadoPor(String creadoPor) {
		this.creadoPor = creadoPor;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdAsistencia() {
		return idAsistencia;
	}
	public void setIdAsistencia(String idAsistencia) {
		this.idAsistencia = idAsistencia;
	}
	public String getTipoDato() {
		return tipoDato;
	}
	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}
	public String getDato() {
		return dato;
	}
	public void setDato(String dato) {
		this.dato = dato;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Boolean isAsistenciaFinalizada() {
		return asistenciaFinalizada;
	}
	public void setAsistenciaFinalizada(Boolean asistenciaFinalizada) {
		this.asistenciaFinalizada = asistenciaFinalizada;
	}
}