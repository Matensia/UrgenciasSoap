package ar.edu.ubp.das.token;

import java.sql.SQLException;

import ar.edu.ubp.das.bean.TokenBean;
import ar.edu.ubp.das.db.Dao;
import ar.edu.ubp.das.db.DaoFactory;

public class TokenValidator {

	public boolean ValidarToken(String token) {
		try {
			Dao<TokenBean, TokenBean> daoToken = DaoFactory.getDao("Token", "ar.edu.ubp.das");
			
			TokenBean tokenBean = new TokenBean();
			
			tokenBean.setToken(token);

			return daoToken.valid(tokenBean);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
