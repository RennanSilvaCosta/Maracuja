package service;

import com.google.gson.Gson;
import dto.CredenciaisDTO;
import dto.NewUserDTO;
import http.HttpUsuario;
import model.UsuarioModel;
import util.Constantes;

import java.io.IOException;
import java.util.Map;

public class UsuarioService {

    private final HttpUsuario httpUsuario = new HttpUsuario();
    private Gson gson = new Gson();

    public Map<Integer, String> logIn(CredenciaisDTO cred) {
        try {
            return httpUsuario.autenticated(Constantes.URL_BASE_PROD + "/login", gson.toJson(cred, CredenciaisDTO.class), Constantes.getPOST());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<Integer, String> createNewUser(NewUserDTO user) {
        try {
           return httpUsuario.createNewUser(Constantes.URL_BASE_PROD + "/usuarios", gson.toJson(user, NewUserDTO.class), Constantes.getPOST());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UsuarioModel getUserLogged(String token) {
        try {
            return gson.fromJson(httpUsuario.sendGET(Constantes.URL_BASE_PROD + "/auth/user_auth", Constantes.getGET(), token), UsuarioModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
