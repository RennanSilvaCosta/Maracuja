package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.UsuarioDAO;
import dto.CredenciaisDTO;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.UsuarioModel;
import service.UsuarioService;
import validator.ValidatorFormLogin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerFXMLTelaLogin {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    JFXTextField txtEmail;

    @FXML
    JFXPasswordField txtSenha;

    @FXML
    Label labelEmailError, labelSenhaError;

    @FXML
    JFXButton btnlogin = new JFXButton();

    UsuarioDAO dao = new UsuarioDAO();
    ValidatorFormLogin validatorFormLogin = new ValidatorFormLogin();
    Map<Integer, String> response = new HashMap<>();
    Map<String, String> erros = new HashMap<>();

    @FXML
    private void login() {
        CredenciaisDTO cred = new CredenciaisDTO();
        UsuarioService usuarioService = new UsuarioService();
        UsuarioModel user;

        erros.clear();
        erros = validatorFormLogin.loginFormValidation(txtEmail.getText(), txtSenha.getText());
        if (erros.isEmpty()) {
            cred.setEmail(txtEmail.getText());
            cred.setSenha(txtSenha.getText());
            response.clear();
            response = usuarioService.logIn(cred);
            if (response.containsKey(200)) {
                //popular objeto usuario e enviar para a tela principal
                dao.saveToken(response.get(200));
                user = usuarioService.getUserLogged(response.get(200));
                loadNewViewAndCloseOld("/view/MainScreen.fxml", btnlogin, user);
            } else {
                //exibir error para o usuario
                setResponseHttp(response);
            }
        } else {
            setErroFormLoginValidator(erros);
        }
    }

    @FXML
    public void loadNewViewAndCloseOld(String caminho, JFXButton componente, UsuarioModel user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(caminho));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            ControllerFXMLTelaPrincipal tela = fxmlLoader.getController();
            tela.initializeInfoUser(user);

            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

            if (componente != null) {
                //a partir do componenete de layout recupero a janela a ser fechada
                Stage stage2 = (Stage) componente.getScene().getWindow();
                stage2.close();
            }

            scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                    stage.setOpacity(0.7);
                }
            });

            scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage.setOpacity(1);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openRegisterScreen() {
        loadNewViewAndCloseOld("/view/RegisterScreen.fxml", null);
    }

    public void loadNewViewAndCloseOld(String caminho, JFXButton componente) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(caminho));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

            if (componente != null) {
                //a partir do componenete de layout recupero a janela a ser fechada
                Stage stage2 = (Stage) componente.getScene().getWindow();
                stage2.close();
            }

            scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                    stage.setOpacity(0.7);
                }
            });

            scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage.setOpacity(1);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setResponseHttp(Map<Integer, String> responseHttp) {
        Set<Integer> codeResponse = responseHttp.keySet();
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Error ao o fazer login");

        if (codeResponse.contains(401)) {
            alerta.setContentText(responseHttp.get(401));
            alerta.showAndWait();
        } else if (codeResponse.contains(500)) {
            alerta.setContentText(responseHttp.get(500));
            alerta.showAndWait();
        }
    }

    private void setErroFormLoginValidator(Map<String, String> erros) {
        Set<String> fields = erros.keySet();
        clearLabelsErros();
        if (fields.contains("email")) {
            labelEmailError.setText(erros.get("email"));
        }
        if (fields.contains("senha")) {
            labelSenhaError.setText(erros.get("senha"));
        }
    }

    private void clearLabelsErros() {
        labelEmailError.setText("");
        labelSenhaError.setText("");
    }

    @FXML
    private void encerrarAplicacao() {
        Platform.exit();
    }

}
