package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.NewUserDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import service.UsuarioService;
import validator.ValidatorFormRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerFXMLTelaCadastro {

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

    public void sair() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    public boolean criarNovaConta() {
        btnCriarConta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errors.clear();
                clearLabelsErrors();
                alterColorInputsForDefault();
                errors = validatorFormRegister.registrationFormValidation(txtNomeEmpresa.getText(), txtNomeUsuario.getText(), txtEmail.getText(), txtSenha.getText(), txtConfirmarSenha.getText());
                if (errors.isEmpty()) {
                    usuarioService.createNewUser(new NewUserDTO(txtNomeEmpresa.getText(), txtNomeUsuario.getText(), txtEmail.getText(), txtSenha.getText()));
                } else {
                    setErrorMessages(errors);
                }
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
}
