package ar.edu.ubp.das.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ar.edu.ubp.das.bean.EntidadBean;
import ar.edu.ubp.das.db.Dao;

public class MSEntidadDao extends Dao<EntidadBean, EntidadBean> {

	@Override
	public EntidadBean delete(EntidadBean entidad) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntidadBean insert(EntidadBean entidad) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntidadBean make(ResultSet resultSet) throws SQLException {
		EntidadBean entidad = new EntidadBean();
		
		entidad.setEstado(resultSet.getString("estado"));
		entidad.setFecha(resultSet.getDate("fecha"));
		entidad.setId(resultSet.getString("id"));
		entidad.setPassword(resultSet.getString("password"));
		entidad.setUsuario(resultSet.getString("usuario"));

		return entidad;
	}

	@Override
	public List<EntidadBean> select(EntidadBean entidad) throws SQLException {
		try {
			this.connect();
			
			this.setProcedure("dbo.VALIDAR_ENTIDAD(?,?)");
			this.setParameter(1, entidad.getUsuario());
			this.setParameter(2, entidad.getPassword());
			
			return this.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return null;
		}
	}

	@Override
	public EntidadBean update(EntidadBean entidad) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean valid(EntidadBean entidad) throws SQLException {
		return false;		
	}

}
