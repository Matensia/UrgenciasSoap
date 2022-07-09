package ar.edu.ubp.das.daos;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;

import ar.edu.ubp.das.bean.DetalleAsistenciaBean;
import ar.edu.ubp.das.db.Dao;

public class MSDetalleAsistenciaDao extends Dao<DetalleAsistenciaBean, DetalleAsistenciaBean> {

	@Override
	public DetalleAsistenciaBean delete(DetalleAsistenciaBean asistenciaReq) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DetalleAsistenciaBean insert(DetalleAsistenciaBean detalleAsistenciaReq) throws SQLException {
		try {
			
			this.connect();
			
			this.setProcedure("dbo.INSERTAR_DETALLE_ASISTENCIA(?,?,?,?)");
			
			this.setParameter(1, detalleAsistenciaReq.getIdAsistencia());
			this.setParameter(2, detalleAsistenciaReq.getTipoDato());
			this.setParameter(3, detalleAsistenciaReq.getDato());
			
			String creadoPor = detalleAsistenciaReq.getCreadoPor() != null ?detalleAsistenciaReq.getCreadoPor():"Usuario";
			this.setParameter(4, creadoPor);
			
			this.executeUpdate();
			
			return detalleAsistenciaReq;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
			
		} finally {
			this.close();
		}	
	}

	@Override
	public DetalleAsistenciaBean make(ResultSet resultSet) throws SQLException {
		DetalleAsistenciaBean detalleAsistenciaBean = new DetalleAsistenciaBean();
		
		detalleAsistenciaBean.setId(resultSet.getInt("id"));
		detalleAsistenciaBean.setIdAsistencia(resultSet.getString("id_solicitud"));
		detalleAsistenciaBean.setFecha(resultSet.getString("fecha"));
		detalleAsistenciaBean.setTipoDato(resultSet.getString("tipo_dato"));
		detalleAsistenciaBean.setDato(resultSet.getString("dato"));
		detalleAsistenciaBean.setCreadoPor(resultSet.getString("creado_por"));
		
		String estado = resultSet.getString("estado");
		//Si esta cerrado o cancelado marcamos la asistencia como finalizada
		detalleAsistenciaBean.setAsistenciaFinalizada(estado != null && (estado.contentEquals("cerrado") || estado.contentEquals("cancelado")));

		return detalleAsistenciaBean;
	}

	@Override
	public List<DetalleAsistenciaBean> select(DetalleAsistenciaBean asistenciaReq) throws SQLException {
		
		try {
			this.connect();
			
			this.setProcedure("dbo.OBTENER_DETALLE_ASISTENCIA()");

			return this.executeQuery();
			
		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}
	}

	@Override
	public DetalleAsistenciaBean update(DetalleAsistenciaBean asistenciaReq) throws SQLException {
		this.connect();
		
		this.setProcedure("dbo.MarcarMensajesEnviados(?)");
		this.setParameter(1, asistenciaReq.getId());
		this.executeUpdate();
		return asistenciaReq;
	}

	@Override
	public boolean valid(DetalleAsistenciaBean detalleAsistenciaReq) throws SQLException {
		return false;
	}

}
