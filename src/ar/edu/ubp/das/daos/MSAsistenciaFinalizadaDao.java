package ar.edu.ubp.das.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ar.edu.ubp.das.bean.AsistenciasFinalizadasBean;
import ar.edu.ubp.das.db.Dao;

public class MSAsistenciaFinalizadaDao extends Dao<AsistenciasFinalizadasBean,AsistenciasFinalizadasBean> {

	@Override
	public AsistenciasFinalizadasBean delete(AsistenciasFinalizadasBean arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AsistenciasFinalizadasBean insert(AsistenciasFinalizadasBean arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AsistenciasFinalizadasBean make(ResultSet resultSet) throws SQLException {
		AsistenciasFinalizadasBean asistencia = new AsistenciasFinalizadasBean();
		
		asistencia.setIdSolicitud(resultSet.getString("id_solicitud"));
		asistencia.setEstado(resultSet.getString("estado"));	
		asistencia.setFechaFinalizada(resultSet.getTimestamp("fecha_cierre"));
		asistencia.setIdServicio(resultSet.getString("id_servicio"));
		
		return asistencia;
	}

	@Override
	public List<AsistenciasFinalizadasBean> select(AsistenciasFinalizadasBean arg0) throws SQLException {
		this.connect();
		this.setProcedure("dbo.OBTENER_ASISTENCIAS_FINALIZADAS");
		
		return this.executeQuery();
	}

	@Override
	public AsistenciasFinalizadasBean update(AsistenciasFinalizadasBean arg0) throws SQLException {
		this.connect();
		this.setProcedure("dbo.MARCAR_CHAT_CERRADO(?)");
		this.setParameter(1, arg0.getIdSolicitud());
		
		this.executeUpdate();
		return arg0;
	}

	@Override
	public boolean valid(AsistenciasFinalizadasBean arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
