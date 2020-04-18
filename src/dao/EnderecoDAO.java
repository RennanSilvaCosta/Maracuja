package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.EnderecoModel;

public class EnderecoDAO {

	private Connection con;

	public EnderecoDAO() {
		this.con = DAOConnectionFactory.getConnection();
	}

	public void inserirEndereco(EnderecoModel endereco) {

		String comandoSQL = "";
		try {
			PreparedStatement prst = this.con.prepareStatement(comandoSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public EnderecoModel consultarCEP(String cep) {

		String comandoSQL = "SELECT * FROM endereco WHERE cep = ?";
		EnderecoModel endereco = new EnderecoModel();

		try {
			PreparedStatement prst = this.con.prepareStatement(comandoSQL);
			prst.setString(1, cep);

			ResultSet resultSet = prst.executeQuery();

			if (resultSet.next()) {
				endereco.setCEP(resultSet.getString("cep"));
				endereco.setLogradouro(resultSet.getString("logradouro"));
				endereco.setBairro(resultSet.getString("bairro"));
				return endereco;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void inserirNovoEndereco(EnderecoModel endereco){

		String comandoSQL = "INSERT INTO endereco (cep, logradouro, bairro) VALUES(?,?,?)";

		try {
			PreparedStatement ppStm = this.con.prepareStatement(comandoSQL);
			ppStm.setString(1, endereco.getCEP());
			ppStm.setString(2, endereco.getLogradouro());
			ppStm.setString(3, endereco.getBairro());
			ppStm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
