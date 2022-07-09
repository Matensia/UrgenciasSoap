package ar.edu.ubp.das.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;



@WebService(targetNamespace = "http://ws.das.ubp.edu.ar/", portName = "PingUrgenciasWSPort", serviceName = "PingUrgenciasWSService")
public class PingUrgenciasWS {
	


	
	@WebMethod(operationName = "getPing", action = "urn:GetPing")
	public String getPing() {
		
		return "pong";
	}

}
