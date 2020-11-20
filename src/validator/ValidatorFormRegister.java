package validator;

import validator.exceptions.ValidatorException;
import validator.exceptions.ValidatorExceptionsMessage;

import java.util.Map;

import static util.Constantes.*;

public class ValidatorFormRegister {

    ValidatorException exception = new ValidatorException();

    public Map<String, String> registrationFormValidation(String nomeEmpresa, String nomeUsuario, String email, String senha, String confirmarSenha) {
        validateNomeEmpresa(nomeEmpresa);
        validateNomeUsuario(nomeUsuario);
        validateEmail(email);
        validateSenha(senha, confirmarSenha);
        return exception.getErrors();
    }

    private void validateNomeEmpresa(String nomeEmpresa) {
        if (nomeEmpresa == null || nomeEmpresa.trim().equals("")) {
            exception.addError("nomeEmpresa", ValidatorExceptionsMessage.NOMEEMPRESA_NULL);
        } else if (nomeEmpresa.length() < MIN_CARACTER_NOME_EMPRESA) {
            exception.addError("nomeEmpresa", ValidatorExceptionsMessage.NOMEEMPRESA_MIN_CARAC_NAO_ATINGIDO);
        } else if (nomeEmpresa.length() > MAX_CARACTER_NOME_EMPRESA) {
            exception.addError("nomeEmpresa", ValidatorExceptionsMessage.NOMEEMPRESA_MAX_CARAC_EXCEDIDO);
        }
    }

    private void validateNomeUsuario(String nomeUsuario) {
        if (nomeUsuario == null || nomeUsuario.trim().equals("")) {
            exception.addError("nomeUsuario", ValidatorExceptionsMessage.NOMEUSUARIO_NULL);
        } else if (nomeUsuario.length() < MIN_CARACTER_NOME_USUARIO) {
            exception.addError("nomeUsuario", ValidatorExceptionsMessage.NOMEUSUARIO_MIN_CARAC_NAO_ATINGINDO);
        } else if (nomeUsuario.length() > MAX_CARACTER_NOME_USUARIO) {
            exception.addError("nomeUsuario", ValidatorExceptionsMessage.NOMEUSUARIO_MAX_CARAC_EXCEDIDO);
        } else if (!nomeUsuario.matches("[a-zA-Z\\s]+")) {
            exception.addError("nomeUsuario", ValidatorExceptionsMessage.NOMEUSUARIO_CONTEM_NUM_OU_ACENTOS);
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().equals("")) {
            exception.addError("email", ValidatorExceptionsMessage.EMAIL_NULL);
        } else if (!email.matches(EMAIL_VALIDO_REGEX)) {
            exception.addError("email", ValidatorExceptionsMessage.EMAIL_INVALIDO);
        }
    }

    private void validateSenha(String senha, String confrimarSenha) {
        if (senha == null || senha.trim().equals("")) {
            exception.addError("senha", ValidatorExceptionsMessage.SENHA_NULL);
        } else if (senha.length() < MIN_CARACTER_PASSWORD) {
            exception.addError("senha", ValidatorExceptionsMessage.SENHA_MIN_CARC_NAO_ATINGIDO);
        } else if (!senha.equals(confrimarSenha)) {
            exception.addError("confirmarSenha", ValidatorExceptionsMessage.SENHAS_NAO_COINCIDEM);
        }
    }
}
