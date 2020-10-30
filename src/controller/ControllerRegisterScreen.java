package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.NewUserDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import service.UsuarioService;
import validator.ValidatorFormRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerRegisterScreen {

    @FXML
    ProgressIndicator progress;

    @FXML
    JFXButton btnSair, btnCriarConta;

    @FXML
    JFXTextField txtNomeEmpresa, txtNomeUsuario, txtEmail;

    @FXML
    JFXPasswordField txtSenha, txtConfirmarSenha;

    @FXML
    Label labelNomeEmpresaError, labelNomeUsuarioError, labelEmailError, labelSenhaError, labelConfirmarSenhaError;

    Map<String, String> errors = new HashMap<>();

    ValidatorFormRegister validatorFormRegister = new ValidatorFormRegister();
    UsuarioService usuarioService = new UsuarioService();

    public boolean criarNovaConta() {
        btnCriarConta.setOnAction(actionEvent -> {
            errors.clear();
            clearLabelsErrors();
            alterColorInputsForDefault();
            errors = validatorFormRegister.registrationFormValidation(txtNomeEmpresa.getText(), txtNomeUsuario.getText(), txtEmail.getText(), txtSenha.getText(), txtConfirmarSenha.getText());
            if (errors.isEmpty()) {
                progress.setVisible(true);
                new addUsuario().start();
            } else {
                setErrorMessages(errors);
            }
        });
        return true;
    }

    private void setErrorMessages(Map<String, String> errors) {
        String colorRed = "#ff0000";
        Set<String> fields = errors.keySet();
        clearLabelsErrors();
        alterColorInputsForDefault();

        if (fields.contains("nomeEmpresa")) {
            txtNomeEmpresa.setUnFocusColor(Paint.valueOf(colorRed));
            labelNomeEmpresaError.setText(errors.get("nomeEmpresa"));
        }
        if (fields.contains("nomeUsuario")) {
            txtNomeUsuario.setUnFocusColor(Paint.valueOf(colorRed));
            labelNomeUsuarioError.setText(errors.get("nomeUsuario"));
        }
        if (fields.contains("email")) {
            txtEmail.setUnFocusColor(Paint.valueOf(colorRed));
            labelEmailError.setText(errors.get("email"));
        }
        if (fields.contains("senha")) {
            txtSenha.setUnFocusColor(Paint.valueOf(colorRed));
            labelSenhaError.setText(errors.get("senha"));
        }
        if (fields.contains("confirmarSenha")) {
            txtConfirmarSenha.setUnFocusColor(Paint.valueOf(colorRed));
            labelConfirmarSenhaError.setText(errors.get("confirmarSenha"));
        }
    }

    private void clearLabelsErrors() {
        labelNomeEmpresaError.setText("");
        labelNomeUsuarioError.setText("");
        labelEmailError.setText("");
        labelSenhaError.setText("");
        labelConfirmarSenhaError.setText("");
    }

    private void alterColorInputsForDefault() {
        String colorYellow = "#ffca28";
        txtNomeEmpresa.setUnFocusColor(Paint.valueOf(colorYellow));
        txtNomeUsuario.setUnFocusColor(Paint.valueOf(colorYellow));
        txtEmail.setUnFocusColor(Paint.valueOf(colorYellow));
        txtSenha.setUnFocusColor(Paint.valueOf(colorYellow));
        txtConfirmarSenha.setUnFocusColor(Paint.valueOf(colorYellow));
    }

    private void disableJTextFields() {
        txtNomeEmpresa.setDisable(true);
        txtNomeUsuario.setDisable(true);
        txtEmail.setDisable(true);
        txtSenha.setDisable(true);
        txtConfirmarSenha.setDisable(true);
        btnCriarConta.setDisable(true);
        btnSair.setDisable(true);
    }

    public void sair() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    public class addUsuario extends Thread {
        @Override
        public void run() {
            progress.setVisible(true);
            disableJTextFields();
            usuarioService.createNewUser(new NewUserDTO(txtNomeEmpresa.getText(), txtNomeUsuario.getText(), txtEmail.getText(), txtSenha.getText()));
            progress.setVisible(false);
            Platform.runLater(() -> {
                Alert dialog = new Alert(Alert.AlertType.INFORMATION, "OK", ButtonType.OK);
                dialog.setTitle("Sucesso");
                dialog.setHeaderText("Conta criada!");
                dialog.setContentText("Sua conta foi criada com sucesso, seja bem vindo!");
                dialog.showAndWait()
                        .filter(resposta -> resposta.equals(ButtonType.OK))
                        .ifPresent(resposta-> sair());
            });
        }
    }
}
