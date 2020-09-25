package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    private String querySQL;
    private Connection conn;
    private PreparedStatement ppstm;

    public void saveToken(String token) {
        try {
            conn = DaoFactory.getConnection();
            querySQL = "DELETE FROM user_token";
            ppstm = conn.prepareStatement(querySQL);
            ppstm.execute();
            querySQL = "INSERT INTO user_token VALUES (?)";
            ppstm = conn.prepareStatement(querySQL);
            ppstm.setString(1, token);
            ppstm.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getToken() {
        try {
            conn = DaoFactory.getConnection();
            querySQL = "SELECT * FROM user_token";
            ppstm = conn.prepareStatement(querySQL);
            ResultSet rs = ppstm.executeQuery();
            if (rs != null && rs.next()){
                String token =  rs.getString("token");
                conn.close();
                return token;
            }
            else {
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
