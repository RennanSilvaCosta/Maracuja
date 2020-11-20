package validator.exceptions;

import static util.Constantes.*;

public class ValidatorExceptionsMessage {

    //FORMULARIO DE REGISTRO
    public static final String NOMEEMPRESA_MIN_CARAC_NAO_ATINGIDO = "O nome da empresa deve conter no minimo " + MIN_CARACTER_NOME_EMPRESA + " caracteres.";
    public static final String NOMEEMPRESA_MAX_CARAC_EXCEDIDO = "O nome da empresa deve ter no máximo " + MAX_CARACTER_NOME_EMPRESA + " caracteres.";
    public static final String NOMEEMPRESA_NULL = "O nome da empresa é obrigatório.";
    public static final String NOMEUSUARIO_MIN_CARAC_NAO_ATINGINDO = "O nome de usuario deve conter no minimo " + MIN_CARACTER_NOME_USUARIO + " caracteres.";
    public static final String NOMEUSUARIO_MAX_CARAC_EXCEDIDO = "O nome de usuario deve ter no máximo " + MAX_CARACTER_NOME_USUARIO + " caracteres.";
    public static final String NOMEUSUARIO_NULL = "O nome de usuario é obrigatório.";
    public static final String NOMEUSUARIO_CONTEM_NUM_OU_ACENTOS = "O nome de usuario não pode conter números e acentos.";
    public static final String EMAIL_NULL = "O email é obrigatório.";
    public static final String EMAIL_INVALIDO = "Email inválido.";
    public static final String SENHA_NULL = "A senha é obrigatória.";
    public static final String SENHA_MIN_CARC_NAO_ATINGIDO = "A senha deve conter no minimo " + MIN_CARACTER_PASSWORD + " caracteres.";
    public static final String SENHAS_NAO_COINCIDEM = "A senhas não coincidem.";
    public static final String SERVER_ERROR = "Algo deu errado, tente novamente mais tarde";

    //FORMULARIO DE REGISTRO ALERT DIALOG SUCCESS
    public static final String FORM_REGISTER_TITLE_ALERT_SUCCESS = "Sucesso!";
    public static final String FORM_REGISTER_HEADER_ALERT_SUCCESS = "Conta criada!";
    public static final String FORM_REGISTER_CONTENTTEXT_ALERT_SUCCESS = "Sua conta foi criada com sucesso, seja bem vindo!";

    //FORMULARIO DE REGISTRO ALERT DIALOG FAIL
    public static final String FORM_REGISTER_TITLE_ALERT_FAIL = "Erro";
    public static final String FORM_REGISTER_HEADER_ALERT_FAIL = "Email Inválido!";
    public static final String EMAIL_JA_CADASTRADO = "O email informado já está cadastrado!";

    //FORMULARIO DE LOGIN
    public static final String FORM_LOGIN_TITLE_ALERT = "Erro";
    public static final String FORM_LOGIN_HEADER_ALERT = "Error ao o fazer login";

}
