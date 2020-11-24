package dto;

import model.EmpresaModel;
import model.EnderecoModel;

import java.io.Serializable;

public class NewEnderecoDTO implements Serializable {

    private String cep;
    private String logradouro;
    private String bairro;
    private EmpresaModel empresa;

    public NewEnderecoDTO () {}

    public NewEnderecoDTO (EnderecoModel enderecoModel) {
        this.cep = enderecoModel.getCep();
        this.logradouro = enderecoModel.getLogradouro();
        this.bairro = enderecoModel.getBairro();
        this.empresa = enderecoModel.getEmpresa();
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public EmpresaModel getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaModel empresa) {
        this.empresa = empresa;
    }
}
