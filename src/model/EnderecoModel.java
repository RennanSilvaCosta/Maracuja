package model;

public class EnderecoModel {

	private String CEP;
	private String bairro;
	private String logradouro;

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cEP) {
		CEP = cEP;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String endereco) {
		logradouro = endereco;
	}

}
