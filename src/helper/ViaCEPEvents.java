package helper;

import model.CepModel;

public interface ViaCEPEvents {

	public void onCEPSuccess(CepModel cep);

	public void onCEPError(String cep);

}
