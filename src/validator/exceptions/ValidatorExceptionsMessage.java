package validator.exceptions;

import static util.Constantes.*;

public class ValidatorExceptionsMessage {

    //FORMULARIO DE REGISTRO
    public static final String NOMEEMPRESA_MIN_CARAC_NAO_ATINGIDO = "O nome da empresa deve conter no minimo " + MIN_CARACTER_NOME_EMPRESA + " caracteres.";
    public static final String NOMEEMPRESA_MAX_CARAC_EXCEDIDO = "O nome da empresa deve ter no máximo " + MAX_CARACTER_NOME_EMPRESA + " caracteres.";
    public static final String NOMEEMPRESA_NULL = "O nome da empresa não pode ser nulo.";
    public static final String NOMEUSUARIO_MIN_CARAC_NAO_ATINGINDO = "O nome de usuario deve conter no minimo " + MIN_CARACTER_NOME_USUARIO + " caracteres.";
    public static final String NOMEUSUARIO_MAX_CARAC_EXCEDIDO = "O nome de usuario deve ter no máximo " + MAX_CARACTER_NOME_USUARIO + " caracteres.";
    public static final String NOMEUSUARIO_NULL = "O nome de usuario não pode ser nulo.";
    public static final String NOMEUSUARIO_CONTEM_NUM_OU_ACENTOS = "O nome de usuario não pode conter números e acentos.";
    public static final String EMAIL_NULL = "O email não pode ser nulo.";
    public static final String EMAIL_INVALIDO = "Email inválido.";
    public static final String SENHA_NULL = "A senha não pode ser nula.";
    public static final String SENHA_MIN_CARC_NAO_ATINGIDO = "A senha deve conter no minimo " + MIN_CARACTER_PASSWORD + " caracteres.";
    public static final String SENHAS_NAO_COINCIDEM = "A senhas não coincidem.";
}