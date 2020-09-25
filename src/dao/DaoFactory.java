package dao;

import util.Constantes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Constantes.URL_LOCAL_DB, Constantes.USER_NAME_DB, Constantes.PASSWORD_DB);
    }
}
