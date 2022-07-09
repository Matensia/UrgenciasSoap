package ar.edu.ubp.das.bean;

import java.util.List;

public class ChatResponseBean {
	
	private List<DetalleAsistenciaBean> listaMensajes;
	private int estado;
	private String mensajeError;
		
	public List<DetalleAsistenciaBean> getListaMensajes() {
		return listaMensajes;
	}
	public void setListaMensajes(List<DetalleAsistenciaBean> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
	
	
}
