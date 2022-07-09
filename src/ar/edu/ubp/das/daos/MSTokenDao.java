package ar.edu.ubp.das.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ar.edu.ubp.das.bean.TokenBean;
import ar.edu.ubp.das.db.Dao;

public class MSTokenDao extends Dao<TokenBean, TokenBean>{

	@Override
	public TokenBean delete(TokenBean token) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TokenBean insert(TokenBean token) throws SQLException {
		try {
			this.connect();
			
			token.setToken(UUID.randomUUID().toString());
			
			this.setProcedure("dbo.INSERTAR_TOKEN(?,?,?,?)");
			this.setParameter(1, token.getIdEntidad());
			this.setParameter(2, token.getToken());
			this.setParameter(3, token.getFechaCreacion());
			this.setParameter(4, token.getFechaExpiracion());
			
			this.executeUpdate();
			
			return token;
			
		} finally {
			this.close();
		}
	}

	@Override
	public TokenBean make(ResultSet resultSet) throws SQLException {
		TokenBean token = new TokenBean();
		
		token.setFechaCreacion(resultSet.getTimestamp("fecha_creacion"));
		token.setFechaExpiracion(resultSet.getTimestamp("fecha_expiracion"));
		token.setId(resultSet.getInt("id"));
		token.setToken(resultSet.getString("token"));
		token.setIdEntidad(resultSet.getString("id_entidad"));

		return token;
	}

	@Override
	public List<TokenBean> select(TokenBean token) throws SQLException {
		try {
			this.connect();
			this.setProcedure("dbo.OBTENER_TOKEN_BY_ENTIDAD(?)");
			this.setParameter(1, token.getIdEntidad());
			
			List<TokenBean> listaTokens = this.executeQuery();
			
			return listaTokens;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}finally {
			this.close();
		}
	}

	@Override
	public TokenBean update(TokenBean token) throws SQLException {

		try {
			this.connect();

			this.setProcedure("dbo.ACTUALIZAR_FECHA_EXPIRACION(?,?)");

			this.setParameter(1, token.getIdEntidad());
			this.setParameter(2, token.getFechaExpiracion());
			
			this.executeUpdate();
			
			return token;

		} catch (SQLException e) {

			e.printStackTrace();
			return null;
			
		} finally {
			this.close();
		}	
	}

	@Override
	public boolean valid(TokenBean token) throws SQLException {
		this.connect();
		this.setProcedure("dbo.VALIDAR_TOKEN(?)");

		this.setParameter(1, token.getToken());
		
		List<TokenBean> listaTokens = this.executeQuery();
		
		return !listaTokens.isEmpty();
	}
	

	public boolean valid(String token, String usuario) {

		boolean any = false;
		Connection conn;
		CallableStatement stmt;
		try {
			Context environment = Context.class.cast((new InitialContext()).lookup("java:comp/env"));	
			Class.forName((String)environment.lookup("ProviderName"));
			conn=DriverManager.getConnection((String)environment.lookup("ConnectionString"));
			conn.setAutoCommit(false);
			try {
				
				stmt = conn.prepareCall("{CALL dbo.VALIDAR_TOKEN(?,?)}");
				stmt.setString(1, token);
				stmt.setString(2, usuario);
				
				ResultSet resultSet = stmt.executeQuery();
				any = resultSet.next();
				
				stmt.close();
				}catch (SQLException e) {
					// TODO: handle exception		
					e.printStackTrace();
					any = false;
				}finally {
					conn.close();
				}
		} catch (ClassNotFoundException | NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			any = false;
		}
		return any;
	}
}
