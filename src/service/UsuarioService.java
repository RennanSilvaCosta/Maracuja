package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.CredenciaisDTO;
import dto.NewUserDTO;
import http.HttpClient;
import model.EmpresaModel;
import model.EnderecoModel;
import model.UsuarioModel;
import util.Constantes;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class UsuarioService {

    private final HttpClient httpClient = new HttpClient();

    private Type listEnderecoType = new TypeToken<List<EnderecoModel>>() {
    }.getType();

    private Type empresaType = new TypeToken<List<EmpresaModel>>() {
    }.getType();

    private Gson gson = new Gson();

    public Map<Integer, String> efetuarLogin(CredenciaisDTO cred) {
        try {
            return httpClient.autenticated(Constantes.URL_BASE_LOCAL + "/login", gson.toJson(cred, CredenciaisDTO.class), Constantes.getPOST());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean criarNovoUsuario(NewUserDTO user) {
        try {
            httpClient.sendPOST(Constantes.URL_BASE_LOCAL + "/usuarios", gson.toJson(user, NewUserDTO.class), Constantes.getPOST());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public UsuarioModel getUserLogged(String token) {
        try {
            UsuarioModel user;
            return user = gson.fromJson(httpClient.sendGET(Constantes.URL_BASE_LOCAL + "/auth/user_auth", Constantes.getGET(), token), UsuarioModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
