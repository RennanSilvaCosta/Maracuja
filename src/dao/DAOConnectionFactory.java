package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Constantes;

public class DAOConnectionFactory {

	public static Connection getConnection() {

		Connection con = null;
		try {
			con = DriverManager.getConnection(Constantes.URL, Constantes.USER, Constantes.PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void closeConnection(Connection con) {

		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException ex) {
			// Log(ex.getMessage(), ex.getCause(), ConnectionFactory.class);
		}
	}

	public static void closeConnection(Connection con, PreparedStatement stmt) {
		closeConnection(con);
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException ex) {
			// Log.logErro(ex.getMessage(), ex.getCause(), ConnectionFactory.class);
		}
	}

	public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
		closeConnection(con, stmt);
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException ex) {
			// Log.logErro(ex.getMessage(), ex.getCause(), ConnectionFactory.class);
		}
	}

}
