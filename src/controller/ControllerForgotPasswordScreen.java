package controller;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.EmailDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.UsuarioService;
import util.Constantes;
import validator.exceptions.ValidatorExceptionsMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerForgotPasswordScreen {

    @FXML
    JFXButton btnSair, btnEnviar;

    @FXML
    Label txtError;

    @FXML
    JFXTextField txtEmail;

    UsuarioService usuarioService = new UsuarioService();
    Map<Integer, String> responseHttp = new HashMap<>();

    public void newPassword() {
        btnEnviar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String email = txtEmail.getText();
                if (email.equals("")) {
                    txtError.setText(ValidatorExceptionsMessage.EMAIL_NULL);
                    new Shake(txtEmail).play();
                } else if (!email.matches(Constantes.EMAIL_VALIDO_REGEX)) {
                    txtError.setText(ValidatorExceptionsMessage.EMAIL_INVALIDO);
                    new Shake(txtEmail).play();
                } else {
                    EmailDTO emailDTO = new EmailDTO();
                    emailDTO.setEmail(email);
                    responseHttp = usuarioService.forgotPassword(emailDTO);
                }
                setMessageHettpResponse(responseHttp);
            }
        });
    }

    private void setMessageHettpResponse(Map<Integer, String> response) {
        Set<Integer> fields = response.keySet();
        if (fields.contains(Constantes.STATUS_CODE_SUCCESSFUL) || fields.contains(Constantes.STATUS_CODE_NOCONTENT)) {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION, "OK", ButtonType.OK);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setTitle("Sucesso!");
            dialog.setHeaderText("Email enviado.");
            dialog.setContentText("Foi enviado um email com sua nova senha.");
            dialog.showAndWait()
                    .filter(resposta -> resposta.equals(ButtonType.OK))
                    .ifPresent(resposta -> sair());

        } else if (fields.contains(Constantes.STATUS_CODE_NOTFOUND)) {
            txtEmail.setUnFocusColor(Paint.valueOf(Constantes.COLOR_RED));
            Alert dialog = new Alert(Alert.AlertType.ERROR, "OK", ButtonType.OK);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setTitle("Email inválido!");
            dialog.setHeaderText("Email não encontrado.");
            dialog.setContentText("O email informado não está cadastrado na base de dados.");
            dialog.showAndWait();
        }
    }

    public void sair() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

}
