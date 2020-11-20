package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.UsuarioDAO;
import dto.NewEnderecoDTO;
import http.HttpEndereco;
import model.EnderecoModel;
import model.UsuarioModel;
import util.Constantes;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class EnderecoService {

    HttpEndereco httpEndereco = new HttpEndereco();
    Gson gson = new Gson();
    UsuarioModel usuarioModel = new UsuarioModel();
    UsuarioDAO dao = new UsuarioDAO();
    UsuarioService usuarioService = new UsuarioService();

    private Type listEnderecoType = new TypeToken<List<EnderecoModel>>() {
    }.getType();

    public EnderecoModel getCepViaCep(String cep) {
        try {
            return gson.fromJson(httpEndereco.sendGET(Constantes.URL_VIA_CEP + cep + "/json", Constantes.getGET()), EnderecoModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public EnderecoModel getCepApiMaracuja(String cep) {
        try {
            usuarioModel = usuarioService.getUserLogged(dao.getToken());
            return gson.fromJson(httpEndereco.sendGET(Constantes.URL_BASE_PROD + "/enderecos/" + cep + "/" + usuarioModel.getEmpresa().getId(), Constantes.getGET()), EnderecoModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String addNewCep(List<String> ceps) {
        NewEnderecoDTO enderecoDTO;
        String token = dao.getToken();
        usuarioModel = usuarioService.getUserLogged(token);
        for (String cep : ceps) {
            try {
                enderecoDTO = gson.fromJson(httpEndereco.sendGET(Constantes.URL_VIA_CEP + cep + "/json", Constantes.getGET()), NewEnderecoDTO.class);
                enderecoDTO.setEmpresa(usuarioModel.getEmpresa());
                httpEndereco.sendPOST(Constantes.URL_BASE_PROD + "/enderecos", gson.toJson(enderecoDTO, NewEnderecoDTO.class), Constantes.getPOST(), token);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<EnderecoModel> getAll() {
        String token = dao.getToken();
        usuarioModel = usuarioService.getUserLogged(token);
        try {
            return new Gson().fromJson(httpEndereco.sendGET(Constantes.URL_BASE_PROD + "/enderecos/" + usuarioModel.getEmpresa().getId(), Constantes.getGET()), listEnderecoType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
