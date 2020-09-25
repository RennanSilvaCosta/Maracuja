package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.UsuarioDAO;
import http.HttpEndereco;
import http.HttpUsuario;
import model.EnderecoModel;
import model.UsuarioModel;
import util.Constantes;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EnderecoService {

    HttpEndereco httpEndereco = new HttpEndereco();
    Gson gson = new Gson();

    private Type listEnderecoType = new TypeToken<List<EnderecoModel>>() {
    }.getType();

    public EnderecoModel searchCepWithViaCep(String cep){
        try {
            return gson.fromJson(httpEndereco.sendGET(Constantes.URL_VIA_CEP + cep + "/json", Constantes.getGET()), EnderecoModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public EnderecoModel searchCepApiMaracuja(String cep){
        UsuarioService usuarioService = new UsuarioService();
        UsuarioDAO dao = new UsuarioDAO();
        try {
            UsuarioModel usuarioModel = usuarioService.getUserLogged(dao.getToken());
            return gson.fromJson(httpEndereco.sendGET(Constantes.URL_BASE_LOCAL + "/enderecos/" + cep + "/" + usuarioModel.getEmpresa().getId(), Constantes.getGET()), EnderecoModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String addNewCep(List<String> ceps) {
        List<EnderecoModel> end = new ArrayList<>();
        for (String cep : ceps) {
            try {
               end.add(gson.fromJson(httpEndereco.sendGET(Constantes.URL_VIA_CEP + cep + "/json", Constantes.getGET()), EnderecoModel.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
