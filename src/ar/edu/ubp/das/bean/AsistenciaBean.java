package ar.edu.ubp.das.bean;


public class AsistenciaBean {
	
	private int 			id;
	private String 			idServicio;
	private String 			estado;
	private String 			idSolicitud;
	private String 			fechaCreacion;
	private String 			fechaCierre;
	private int 			dni;	
	private int             id_asistente;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getFechaCierre() {
		return fechaCierre;
	}
	public void setFechaCierre(String fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int cuil) {
		this.dni = cuil;
	}
	public int getId_asistente() {
		return id_asistente;
	}
	public void setId_asistente(int id_asistente) {
		this.id_asistente = id_asistente;
	}	
}