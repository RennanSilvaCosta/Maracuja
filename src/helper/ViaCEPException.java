package helper;

public class ViaCEPException extends Exception {

	private String CEP;
	private String Classe;

	/**
	 * Gera uma nova exce��o
	 * 
	 * @param message descri��o do erro
	 * @param classe  classe da excess�o original
	 */
	public ViaCEPException(String message, String classe) {
		super(message);

		this.CEP = "";
		this.Classe = classe;
	}

	/**
	 * Gera uma nova exce��o e define o CEP que foi solicitado
	 * 
	 * @param message descri��o do erro
	 * @param cep     CEP que foi usado durante o processo
	 * @param classe  classe da excess�o original
	 */
	public ViaCEPException(String message, String cep, String classe) {
		super(message);

		this.CEP = cep;
		this.Classe = classe;
	}

	/**
	 * Define o CEP da exce��o
	 * 
	 * @param cep
	 */
	public void setCEP(String cep) {
		this.CEP = cep;
	}

	/**
	 * Retorna o CEP da exce��o
	 * 
	 * @return String CEP
	 */
	public String getCEP() {
		return this.CEP;
	}

	/**
	 * Retorna se tem algum CEP
	 * 
	 * @return boolean
	 */
	public boolean hasCEP() {
		return !this.CEP.isEmpty();
	}

	/**
	 * Retorna a classe da excess�o original
	 * 
	 * @return
	 */
	public String getClasse() {
		return Classe;
	}

}
