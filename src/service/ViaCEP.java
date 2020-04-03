package service;

import helper.ViaCEPBase;
import helper.ViaCEPEvents;
import helper.ViaCEPException;
import model.CepModel;
import org.json.JSONArray;
import org.json.JSONObject;

public class ViaCEP extends ViaCEPBase {

	public static final double VIACEP_VERSAO = 0.33;

	public ViaCEP() {
		super();
	}

	public ViaCEP(ViaCEPEvents events) {
		super();
		this.Events = events;
	}

	public ViaCEP(String cep, ViaCEPEvents events) throws ViaCEPException {
		super();
		this.Events = events;
		this.buscar(cep);
	}

	public ViaCEP(String cep) throws ViaCEPException {
		super();
		this.buscar(cep);
	}

	public final void buscar(String cep) throws ViaCEPException {
		// define o cep atual
		String currentCEP = cep;

		// define a url
		String url = "http://viacep.com.br/ws/" + cep + "/json/";

		// define os dados
		JSONObject obj = new JSONObject(getHttpGET(url));

		if (!obj.has("erro")) {
			CepModel novoCEP = new CepModel(obj.getString("cep"), obj.getString("logradouro"),
					obj.getString("complemento"), obj.getString("bairro"), obj.getString("localidade"),
					obj.getString("uf"), obj.getString("ibge"), obj.getString("gia"));

			// insere o novo CEP
			CEPs.add(novoCEP);

			// atualiza o index
			index = CEPs.size() - 1;

			// verifica os Eventos
			if (Events instanceof ViaCEPEvents) {
				// Events.onCEPSuccess(this);
			}
		} else {
			// verifica os Eventos
			if (Events instanceof ViaCEPEvents) {
				Events.onCEPError(currentCEP);
			}

			throw new ViaCEPException("Não foi possível encontrar o CEP", cep, ViaCEPException.class.getName());
		}
	}

	public void buscarCEP(CepModel cep) throws ViaCEPException {
		buscarCEP(cep.Uf, cep.Localidade, cep.Logradouro);
	}

	public void buscarCEP(String Uf, String Localidade, String Logradouro) throws ViaCEPException {
		// define o cep atual
		String currentCEP = "?????-???";

		// define a url
		String url = "http://viacep.com.br/ws/" + Uf.toUpperCase() + "/" + Localidade + "/" + Logradouro + "/json/";

		// obtem a lista de CEP's
		JSONArray ceps = new JSONArray(getHttpGET(url));

		if (ceps.length() > 0) {
			for (int i = 0; i < ceps.length(); i++) {
				JSONObject obj = ceps.getJSONObject(i);

				if (!obj.has("erro")) {
					CepModel novoCEP = new CepModel(obj.getString("cep"), obj.getString("logradouro"),
							obj.getString("complemento"), obj.getString("bairro"), obj.getString("localidade"),
							obj.getString("uf"), obj.getString("ibge"), obj.getString("gia"));

					// insere o novo CEP
					CEPs.add(novoCEP);

					// atualiza o index
					int index = CEPs.size() - 1;

					// verifica os Eventos
					if (Events instanceof ViaCEPEvents) {
						Events.onCEPSuccess(novoCEP);
					}
				} else {
					// verifica os Eventos
					if (Events instanceof ViaCEPEvents) {
						Events.onCEPError(currentCEP);
					}

					throw new ViaCEPException("Não foi possível validar o CEP", currentCEP,
							ViaCEPException.class.getName());
				}
			}
		} else {
			throw new ViaCEPException("Nenhum CEP encontrado", currentCEP, getClass().getName());
		}
	}

}
