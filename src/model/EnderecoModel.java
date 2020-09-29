package model;

import java.io.Serializable;

public class EnderecoModel implements Serializable {

    private Integer id;
    private String cep;
    private String logradouro;
    private String bairro;
    private EmpresaModel empresa;

    public EnderecoModel() {
    }

    public EnderecoModel(Integer id, String cep, String logradouro, String bairro) {
        this.id = id;
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cEP) {
        cep = cEP;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String endereco) {
        logradouro = endereco;
    }

    public EmpresaModel getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaModel empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return "CEP: " + this.cep + " Log: " + logradouro + " Bairro: " + bairro;
    }

}
