package ar.edu.ubp.das.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.ubp.das.bean.AsistenciaBean;
import ar.edu.ubp.das.db.Dao;
import ar.edu.ubp.das.utils.Utils;

public class MSAsistenciaDao extends Dao<AsistenciaBean, AsistenciaBean> {

	@Override
	public AsistenciaBean delete(AsistenciaBean asistenciaReq) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AsistenciaBean insert(AsistenciaBean asistenciaReq) throws SQLException {
		try {
			
			AsistenciaBean asistencia = new AsistenciaBean();
			
			this.connect();
			
			this.setProcedure("dbo.CREAR_ASISTENCIA(?,?,?,?)");
			
			this.setParameter(1, asistenciaReq.getIdServicio());
			this.setParameter(2, asistenciaReq.getEstado());
			this.setParameter(3, asistenciaReq.getIdSolicitud());
			this.setParameter(4, asistenciaReq.getDni());
			
			this.executeUpdate();
			
			asistencia.setIdServicio(asistenciaReq.getIdServicio());
			
			return asistencia;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
			
		} finally {
			this.close();
		}	
	}

	@Override
	public AsistenciaBean make(ResultSet resultSet) throws SQLException {
		AsistenciaBean asistenciaBean = new AsistenciaBean();
		
		asistenciaBean.setId(resultSet.getInt("id"));
		asistenciaBean.setIdServicio(resultSet.getString("id_servicio"));
		asistenciaBean.setEstado(resultSet.getString("estado"));
		asistenciaBean.setIdSolicitud(resultSet.getString("id_solicitud"));
		asistenciaBean.setFechaCreacion(resultSet.getString("fecha_creacion"));
		asistenciaBean.setFechaCierre(resultSet.getString("fecha_cierre"));
		asistenciaBean.setDni(resultSet.getInt("dni"));

		return asistenciaBean;
	}

	@Override
	public List<AsistenciaBean> select(AsistenciaBean asistenciaReq) throws SQLException {
		
		try {
			this.connect();
			
			if (asistenciaReq.getDni() != 0) {
				
				this.setProcedure("dbo.OBTENER_ASISTENCIAS_POR_USUARIO(?)");
				this.setParameter(1, asistenciaReq.getDni());
				
			} else if (!Utils.StringNullOrEmpty(asistenciaReq.getIdSolicitud())) {
				
				this.setProcedure("dbo.OBTENER_ASISTENCIA_POR_ID_SOLICITUD(?)");
				this.setParameter(1, asistenciaReq.getIdSolicitud());
			}else {
				return new ArrayList<AsistenciaBean>();
			}

			return this.executeQuery();
		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}
	}

	@Override
	public AsistenciaBean update(AsistenciaBean asistenciaReq) throws SQLException {
		
		try {
			this.connect();

			this.setProcedure("dbo.CERRAR_ASISTENCIA(?)");

			this.setParameter(1, asistenciaReq.getIdSolicitud());
			
			this.executeUpdate();
			
			return asistenciaReq;

		} catch (SQLException e) {

			e.printStackTrace();
			return null;
			
		} finally {
			this.close();
		}	
	}

	@Override
	public boolean valid(AsistenciaBean asistenciaReq) throws SQLException {
		return false;
	}

}
