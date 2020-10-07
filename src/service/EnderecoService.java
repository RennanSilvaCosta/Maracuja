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
        NewEnderecoDTO enderecoDTO;
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioModel user;
        UsuarioService usuarioService = new UsuarioService();
        String token = dao.getToken();
        user = usuarioService.getUserLogged(token);
        for (String cep : ceps) {
            try {
                enderecoDTO = gson.fromJson(httpEndereco.sendGET(Constantes.URL_VIA_CEP + cep + "/json", Constantes.getGET()), NewEnderecoDTO.class);
                enderecoDTO.setEmpresa(user.getEmpresa());
                httpEndereco.sendPOST(Constantes.URL_BASE_LOCAL + "/enderecos", gson.toJson(enderecoDTO, NewEnderecoDTO.class), Constantes.getPOST(), token);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<EnderecoModel> getAll() {
        UsuarioModel user;
        UsuarioService userServicer = new UsuarioService();
        UsuarioDAO dao = new UsuarioDAO();
        String token = dao.getToken();
        user = userServicer.getUserLogged(token);
        try {
            return new Gson().fromJson(httpEndereco.sendGET(Constantes.URL_BASE_LOCAL + "/enderecos/" + user.getEmpresa().getId(), Constantes.getGET()), listEnderecoType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
