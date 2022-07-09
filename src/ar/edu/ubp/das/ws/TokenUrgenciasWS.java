package ar.edu.ubp.das.ws;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.google.gson.Gson;

import ar.edu.ubp.das.bean.EntidadBean;
import ar.edu.ubp.das.bean.ErrorBean;
import ar.edu.ubp.das.bean.TokenBean;
import ar.edu.ubp.das.bean.TokenRequestBean;
import ar.edu.ubp.das.bean.TokenResponseBean;
import ar.edu.ubp.das.db.Dao;
import ar.edu.ubp.das.db.DaoFactory;



@WebService(targetNamespace = "http://ws.das.ubp.edu.ar/", portName = "TokenUrgenciasWSPort", serviceName = "TokenUrgenciasWSService")
public class TokenUrgenciasWS {
	
	//Cantidad de milisegundos en una hora
	static final Long DURATION = ((60l * 60l) * 1000l);
	

	
	@WebMethod(operationName = "getToken", action = "urn:GetToken")
	public String getToken( @WebParam(name = "arg0") String request) {

		Gson gson = new Gson();
		ErrorBean err = new ErrorBean();
		TokenBean tokenGet = new TokenBean();
		TokenBean tokenInsert = new TokenBean();
		TokenBean tokenUpdated = new TokenBean();
		List<TokenBean> tokensGet;
		TokenRequestBean tokenRequest = gson.fromJson(request, TokenRequestBean.class);
		TokenResponseBean response = new TokenResponseBean();
		
		response.setRespuesta(0);
		response.setMensaje("");
		
		Timestamp fecha = new Timestamp(Calendar.getInstance().getTime().getTime());

		EntidadBean entidad = this.validarEntidad(tokenRequest.getUsuario(), tokenRequest.getPassword());

		if (entidad == null) {
			response.setMensaje("No se ha encontrado una entidad activa que corresponda con las credenciales ingresadas");
			response.setRespuesta(0);
			return gson.toJson(response);
		}
		
		try {
			Dao<TokenBean, TokenBean> dao = DaoFactory.getDao("Token", "ar.edu.ubp.das");
			
			tokenGet.setIdEntidad(entidad.getId());

			tokensGet = dao.select(tokenGet);
			
			//SI EL TOKEN EXISTE VALIDAMOS SI ESTA VENCIDO
			if (!tokensGet.isEmpty()) {
				
				int isTokenExpire = tokensGet.get(0).getFechaExpiracion().compareTo(fecha);
			   		
				//SI NO ESTA VENCIDO RETORNAMOS EL TOKEN
		        if (isTokenExpire > 0 || isTokenExpire == 0) {  
		        	
		            System.out.println("fecha expiracion value is greater"); 
		            response.setToken(tokensGet.get(0).getToken());
					response.setFechaExpiracion(tokensGet.get(0).getFechaExpiracion());
					response.setRespuesta(1);
					String tokenGetString = gson.toJson(response);
		            
		            return tokenGetString;
		        }  
		        else {
		        	
		        	//SI EL TOKEN ESTA VENCIDO EXTENEDEMOS 8 HORAS LA FECHA DE EXPIRACION
		            System.out.println("fecha value is greater");  
		            
		            tokensGet.get(0).setFechaExpiracion(new Timestamp(fecha.getTime() + (DURATION * 8)));
		            
		            try {
		    			
		            tokenUpdated = dao.update(tokensGet.get(0));
		            response.setToken(tokenUpdated.getToken());
					response.setFechaExpiracion(tokenUpdated.getFechaExpiracion());
					response.setRespuesta(1);
					String tokenGetString = gson.toJson(response);
		            
		            return tokenGetString;
		    			
		            } catch (SQLException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}     
		        }  
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tokenInsert.setIdEntidad(entidad.getId());
		tokenInsert.setFechaCreacion(fecha);

		// Agregamos 8 horas, se puede cambiar el valor para cambiar la duracion
		tokenInsert.setFechaExpiracion(new Timestamp(fecha.getTime() + (DURATION * 8)));

		try {
			Dao<TokenBean, TokenBean> dao = DaoFactory.getDao("Token", "ar.edu.ubp.das");

			tokenInsert = dao.insert(tokenInsert);

			response.setToken(tokenInsert.getToken());
			response.setFechaExpiracion(tokenInsert.getFechaExpiracion());
			response.setRespuesta(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String tokenResponse = gson.toJson(response);

		return tokenResponse;
	}
	
	private EntidadBean validarEntidad(String usuario, String password) {
		try {
			Dao<EntidadBean, EntidadBean> daoEntidad = DaoFactory.getDao("Entidad", "ar.edu.ubp.das");

			EntidadBean entidad = new EntidadBean();
			entidad.setUsuario(usuario);
			entidad.setPassword(password);

			List<EntidadBean> entidades = daoEntidad.select(entidad);

			return (entidades.isEmpty()) ? null : entidades.get(0);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
