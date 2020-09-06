package validator;

import validator.exceptions.ValidatorException;
import validator.exceptions.ValidatorExceptionsMessage;

import java.util.Map;

import static util.Constantes.EMAIL_VALIDO_REGEX;
import static util.Constantes.MIN_CARACTER_PASSWORD;

public class ValidatorFormLogin {

    ValidatorException exception = new ValidatorException();

    public Map<String, String> loginFormValidation(String email, String senha) {
        validateEmail(email);
        validateSenha(senha);
        return exception.getErrors();
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().equals("")) {
            exception.addError("email", ValidatorExceptionsMessage.EMAIL_NULL);
        } else if (!email.matches(EMAIL_VALIDO_REGEX)) {
            exception.addError("email", ValidatorExceptionsMessage.EMAIL_INVALIDO);
        }
    }

    private void validateSenha(String senha) {
        if (senha == null || senha.trim().equals("")) {
            exception.addError("senha", ValidatorExceptionsMessage.SENHA_NULL);
        } else if (senha.length() < MIN_CARACTER_PASSWORD) {
            exception.addError("senha", ValidatorExceptionsMessage.SENHA_MIN_CARC_NAO_ATINGIDO);
        }
    }

}
