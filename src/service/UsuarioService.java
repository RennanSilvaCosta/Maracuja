package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.CredenciaisDTO;
import dto.NewUserDTO;
import http.HttpClient;
import model.EmpresaModel;
import model.EnderecoModel;
import util.Constantes;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class UsuarioService {

    private final HttpClient httpClient = new HttpClient();

    private Type listEnderecoType = new TypeToken<List<EnderecoModel>>() {
    }.getType();

    private Type empresaType = new TypeToken<List<EmpresaModel>>() {
    }.getType();

    private Gson gson =  new Gson();

    public boolean efetuarLogin(CredenciaisDTO cred)
    {
        try {
            httpClient.sendPOST(Constantes.URL_BASE_LOCAL+"/login", gson.toJson(cred, CredenciaisDTO.class), Constantes.getPOST());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean criarNovoUsuario(NewUserDTO user)
    {
        try {
           String response =  httpClient.sendPOST(Constantes.URL_BASE_LOCAL+"/usuarios", gson.toJson(user, NewUserDTO.class), Constantes.getPOST());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
