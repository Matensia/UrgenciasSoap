package ar.edu.ubp.das.ws;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.google.gson.Gson;

import ar.edu.ubp.das.bean.AsistenciaBean;
import ar.edu.ubp.das.bean.AsistenciaRequestBean;
import ar.edu.ubp.das.bean.AsistenciasFinalizadasBean;
import ar.edu.ubp.das.bean.CerrarAsistenciaReqBean;
import ar.edu.ubp.das.bean.CerrarAsistenciaRespBean;
import ar.edu.ubp.das.bean.ChatRequestBean;
import ar.edu.ubp.das.bean.ChatResponseBean;
import ar.edu.ubp.das.bean.DetalleAsistenciaBean;
import ar.edu.ubp.das.bean.ErrorBean;
import ar.edu.ubp.das.bean.ListaFinalizadosRequestBean;
import ar.edu.ubp.das.bean.ListaFinalizadosResponseBean;
import ar.edu.ubp.das.db.Dao;
import ar.edu.ubp.das.db.DaoFactory;
import ar.edu.ubp.das.token.TokenValidator;
import ar.edu.ubp.das.utils.Utils;


@WebService(targetNamespace = "http://ws.das.ubp.edu.ar/", portName = "AsistenciaUrgenciasWSPort", serviceName = "AsistenciaUrgenciasWSService")
public class AsistenciaUrgenciasWS {
	
	@WebMethod(operationName = "guardarAsistencia", action = "urn:GuardarAsistencia")
	public String guardarAsistencia(@WebParam(name = "arg0") String asistenciaReq) {
			
//		String path = context.getServletContext().getRealPath("/Logger/UrgenciaSoap/");
		Gson gson = new Gson();
		String result = "";
		try {
			
			AsistenciaRequestBean asistenciaRequest = gson.fromJson(asistenciaReq, AsistenciaRequestBean.class);
			AsistenciaBean asistenciaResp;
			ErrorBean err = new ErrorBean();	
			TokenValidator tokenValidator = new TokenValidator();
			
			if(Utils.StringNullOrEmpty(asistenciaRequest.getToken()) || !tokenValidator.ValidarToken(asistenciaRequest.getToken())) {
				err.setMessage("Token no valido");
				
				return gson.toJson(err);
			}
			
			AsistenciaBean asistenciaInsert = new AsistenciaBean();
			AsistenciaBean asistenciaBySolicitud = new AsistenciaBean();
			DetalleAsistenciaBean detAsistenciaReq = new DetalleAsistenciaBean();
			DetalleAsistenciaBean detAsistenciaResp = new DetalleAsistenciaBean();
			List<AsistenciaBean> listaAsistencias;		
			
						
			
			Dao<AsistenciaBean, AsistenciaBean> dao = DaoFactory.getDao("Asistencia", "ar.edu.ubp.das");
			
			asistenciaInsert.setDni(asistenciaRequest.getInformacion().getDni());
			asistenciaInsert.setIdServicio(asistenciaRequest.getInformacion().getIdServicio());
			asistenciaInsert.setEstado("activa");
			asistenciaInsert.setFechaCreacion(asistenciaRequest.getFecha());
			asistenciaInsert.setIdSolicitud(asistenciaRequest.getIdSolicitud());
			
			asistenciaResp = dao.insert(asistenciaInsert);
			
			if (asistenciaResp == null) {
				err.setMessage("Error al insertar asistencia");
				String notCreated = gson.toJson(err); 
				return gson.toJson(notCreated);
			}
	
			result = gson.toJson(asistenciaResp);
			
			if (result != null) {
				
				asistenciaBySolicitud.setIdSolicitud(asistenciaInsert.getIdSolicitud());
				
				listaAsistencias = dao.select(asistenciaBySolicitud);
				
				Dao<DetalleAsistenciaBean, DetalleAsistenciaBean> daoDetAs = DaoFactory.getDao("DetalleAsistencia", "ar.edu.ubp.das");
				
				//INSERT TEXT
				detAsistenciaReq.setIdAsistencia(listaAsistencias.get(0).getIdSolicitud());
				detAsistenciaReq.setTipoDato(asistenciaRequest.getInformacion().getDato().getClass().getSimpleName());
				detAsistenciaReq.setDato(asistenciaRequest.getInformacion().getDato());
				detAsistenciaReq.setFecha(asistenciaRequest.getFecha());
				
				detAsistenciaResp = daoDetAs.insert(detAsistenciaReq);
				
				if (detAsistenciaResp == null) {
					err.setMessage("Error al insertar detalle asistencia");
					String notCreated = gson.toJson(err); 
					return gson.toJson(notCreated);
				}
				
				if (asistenciaRequest.getInformacion().getUbicacion() != null) {
					//INSERT UBICATION
					String ubication = "lat: " + asistenciaRequest.getInformacion().getUbicacion().getLatitud() + " - long: " + asistenciaRequest.getInformacion().getUbicacion().getLongitud();
					
					detAsistenciaReq.setIdAsistencia(listaAsistencias.get(0).getIdSolicitud());
					detAsistenciaReq.setTipoDato(asistenciaRequest.getInformacion().getDato().getClass().getSimpleName());
					detAsistenciaReq.setDato(ubication);
					detAsistenciaReq.setFecha(asistenciaRequest.getFecha());
					
					detAsistenciaResp = daoDetAs.insert(detAsistenciaReq);
					
					if (detAsistenciaResp == null) {
						err.setMessage("Error al insertar detalle asistencia");
						String notCreated = gson.toJson(err); 
						return gson.toJson(notCreated);
					}
				}
			}			
			
		} catch (SQLException e) {
//			ar.edu.ubp.das.logger.Logger.getLogger(path).escribirLog(e);
		}
		return gson.toJson(result!=null?result:"Error");
	}


	
	@WebMethod(operationName = "cerrarAsistencia", action = "urn:CerrarAsistencia")
	public String cerrarAsistencia(@WebParam(name = "arg0") String asistenciaReq) {
		
		Gson gson = new Gson();
		AsistenciaBean updateAsistencia = new AsistenciaBean();
		CerrarAsistenciaRespBean cerrarAsistenciaResp = new CerrarAsistenciaRespBean();
		List<AsistenciaBean> listaAsistencias;
		
		CerrarAsistenciaReqBean asistenciaRequest = gson.fromJson(asistenciaReq, CerrarAsistenciaReqBean.class);
		
//		String path = context.getServletContext().getRealPath("/Logger/BomberosRest/");
		
		try {
			
			Dao<AsistenciaBean, AsistenciaBean> dao = DaoFactory.getDao("Asistencia", "ar.edu.ubp.das");
			
			updateAsistencia.setIdSolicitud(asistenciaRequest.getIdSolicitud());
			
			//VALIDAR ASISTENCIA
			listaAsistencias = dao.select(updateAsistencia);
			
			if (listaAsistencias.size() == 0) {
				
				cerrarAsistenciaResp.setIdSolicitud(asistenciaRequest.getIdSolicitud());
				cerrarAsistenciaResp.setEstado(0);
				cerrarAsistenciaResp.setMensaje("No existe asistencia");
				cerrarAsistenciaResp.setFinalizado(false);
				
				String cerrarAsistenciaRespString = gson.toJson(cerrarAsistenciaResp);
				
				return cerrarAsistenciaRespString;
			}
			
			//CERRAR ASISTENCIA
			dao.update(listaAsistencias.get(0));
			
			cerrarAsistenciaResp.setIdSolicitud(asistenciaRequest.getIdSolicitud());
			cerrarAsistenciaResp.setEstado(1);
			cerrarAsistenciaResp.setMensaje("Asistencia cerrada");
			cerrarAsistenciaResp.setFinalizado(true);
			
			String cerrarAsistenciaRespString = gson.toJson(cerrarAsistenciaResp); 
			
			return cerrarAsistenciaRespString!=null?cerrarAsistenciaRespString:"Error";
			
			
		} catch (SQLException e) {
//			ar.edu.ubp.das.logger.Logger.getLogger(path).escribirLog(e);
			
			cerrarAsistenciaResp.setIdSolicitud(asistenciaRequest.getIdSolicitud());
			cerrarAsistenciaResp.setEstado(0);
			cerrarAsistenciaResp.setMensaje("Asistencia no cerrada");
			cerrarAsistenciaResp.setFinalizado(false);
			
			String cerrarAsistenciaRespString = gson.toJson(cerrarAsistenciaResp);
			
			return cerrarAsistenciaRespString;

		}	
	}
	
	
	
	@WebMethod(operationName = "insertarChat", action = "urn:InsertarChat")
	public String insertarChat(@WebParam(name = "arg0") String asistenciaReq) {
		
//		String path = context.getServletContext().getRealPath("/Logger/BomberosRest/");
		
		DetalleAsistenciaBean detAsistenciaResp = new DetalleAsistenciaBean();
		
		List<DetalleAsistenciaBean> listaDetalleAsistencias = new ArrayList<DetalleAsistenciaBean>();
		
		ChatResponseBean response = new ChatResponseBean();
		
		Gson gson = new Gson();
		
		try {
			ChatRequestBean request = gson.fromJson(asistenciaReq, ChatRequestBean.class);
			
			Dao<DetalleAsistenciaBean, DetalleAsistenciaBean> daoDetAs = DaoFactory.getDao("DetalleAsistencia", "ar.edu.ubp.das");
			
			for (DetalleAsistenciaBean detalleAsistencia : request.getListaMensajes()) {

				detalleAsistencia.setTipoDato("chat");
				
				detAsistenciaResp = daoDetAs.insert(detalleAsistencia);
				
				if (detAsistenciaResp == null) {					
					response.setEstado(0);
					response.setMensajeError("Error al insertar chat");
								
					String notCreated = gson.toJson(response); 
					return notCreated;
				}
				
				
			}
			List<DetalleAsistenciaBean> mensajesAEnviar = daoDetAs.select(null);
			
			if(mensajesAEnviar != null && !mensajesAEnviar.isEmpty())
					listaDetalleAsistencias.addAll(mensajesAEnviar);
			
			if(!listaDetalleAsistencias.isEmpty()) {
				DetalleAsistenciaBean ultimoMensaje = listaDetalleAsistencias.stream().max(Comparator.comparingInt(DetalleAsistenciaBean::getId)).get();
				if(ultimoMensaje != null) {
					//Usando el id mas alto marcamos los mensajes anteriores como leidos
					daoDetAs.update(ultimoMensaje);				
				}
			}			
			
			response.setEstado(1);
			response.setListaMensajes(listaDetalleAsistencias);			
			String listaDetalleAsistenciasString = gson.toJson(response); 
			
			return listaDetalleAsistenciasString!=null?listaDetalleAsistenciasString:"Error";
			
		} catch (SQLException e) {		
//			ar.edu.ubp.das.logger.Logger.getLogger(path).escribirLog(e);
			
			response.setEstado(0);
			response.setMensajeError("Error al obtener chats");
			String notFound = gson.toJson(response); 
			return notFound;
		}
	}
	
	
	@WebMethod(operationName = "obtenerChatsCerrados", action = "urn:ObtenerChatsCerrados")
	public String obtenerChatsCerrados(@WebParam(name = "arg0") String finalizadosReq) {
		
//		String path = context.getServletContext().getRealPath("/Logger/BomberosRest/");
		
		List<AsistenciasFinalizadasBean> listaDetalleAsistencias;
		ListaFinalizadosResponseBean response = new ListaFinalizadosResponseBean();
		Gson gson = new Gson();
		ErrorBean err = new ErrorBean();
		
		try {
			
			ListaFinalizadosRequestBean FinalizadosRequest = gson.fromJson(finalizadosReq, ListaFinalizadosRequestBean.class);
			
			Dao<AsistenciasFinalizadasBean, AsistenciasFinalizadasBean> daoDetAs = DaoFactory.getDao("AsistenciaFinalizada", "ar.edu.ubp.das");
					
			
			if (FinalizadosRequest == null) {
				err.setMessage("Error al insertar chat");
				String notCreated = gson.toJson(err); 
				return notCreated;
			}
			
			listaDetalleAsistencias = daoDetAs.select(null);
						
			response.setEstado(0);			
			response.setIdServicio("defensa_civil");			
			
			if(listaDetalleAsistencias != null) {
				response.setListaAsistenciasFinalizadas(listaDetalleAsistencias);
				for (AsistenciasFinalizadasBean asistenciasFinalizadasBean : listaDetalleAsistencias) {
					daoDetAs.update(asistenciasFinalizadasBean);
				}
			}

			String listaDetalleAsistenciasString = gson.toJson(response); 
						
			return listaDetalleAsistenciasString!=null?listaDetalleAsistenciasString:"Error";
			
		} catch (SQLException e) {
		
//			ar.edu.ubp.das.logger.Logger.getLogger(path).escribirLog(e);
			
			err.setMessage("Error al obtener chats");
			String notFound = gson.toJson(err); 
			
			response.setEstado(1);
			response.setIdServicio("defensa_civil");	
			response.setMensaje(notFound);
			
			return gson.toJson(response);
		}
	}
}
