package model;

public class CepModel {

	public String CEP;
	public String Logradouro;
	public String Complemento;
	public String Bairro;
	public String Localidade;
	public String Uf;
	public String Ibge;
	public String Gia;

	/**
	     * Cria um novo CEP vazio
	     */
	    public CepModel() {
	        this.Logradouro = null;
	        this.Complemento = null;
	        this.Bairro = null;
	        this.Localidade = null;
	        this.Uf = null;
	        this.Ibge = null;
	        this.Gia = null;
	    }

	/**
	     * Cria um novo CEP completo
	     * @param CEP
	     * @param Logradouro
	     * @param Complemento
	     * @param Bairro
	     * @param Localidade
	     * @param Uf
	     * @param Ibge
	     * @param Gia 
	     */
	    public CepModel(String CEP, String Logradouro, String Complemento, String Bairro, String Localidade, String Uf, String Ibge, String Gia) {
	        this.CEP = CEP;
	        this.Logradouro = Logradouro;
	        this.Complemento = Complemento;
	        this.Bairro = Bairro;
	        this.Localidade = Localidade;
	        this.Uf = Uf;
	        this.Ibge = Ibge;
	        this.Gia = Gia;
	    }

	/**
	     * Cria um novo CEP parcial
	     * @param Logradouro
	     * @param Localidade
	     * @param Uf 
	     */
	    public CepModel(String Logradouro, String Localidade, String Uf) {
	        this.Logradouro = Logradouro;
	        this.Localidade = Localidade;
	        this.Uf = Uf;
	    }

}
