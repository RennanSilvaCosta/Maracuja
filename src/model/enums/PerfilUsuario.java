package model.enums;

public enum PerfilUsuario {

    ADMIN(1, "ROLE_ADMIN"), OTHER_USER(2, "ROLE_OTHER_USER");

    private int cod;
    private String descricao;

    PerfilUsuario(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
