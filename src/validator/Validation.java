package validator;

public class Validation {

    public boolean verificarCEP(String cep){

        boolean retorno = true;

        for (int i=0; i< cep.length(); i++) {
            char c = cep.charAt(i);
            if (String.valueOf(c).equals("_")){
                retorno = false;
                break;
            }
        }
        return retorno;
    }
}