package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.UsuarioDAO;
import dto.CredenciaisDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.UsuarioModel;
import service.UsuarioService;
import util.Constantes;
import validator.ValidatorFormLogin;
import validator.exceptions.ValidatorExceptionsMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerLoginScreen {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    JFXTextField txtEmail;

    @FXML
    ProgressIndicator progressIndicator;

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

    CredenciaisDTO cred = new CredenciaisDTO();
    UsuarioService usuarioService = new UsuarioService();
    UsuarioModel user;

    @FXML
    private void login() {
        erros.clear();
        erros = validatorFormLogin.loginFormValidation(txtEmail.getText(), txtSenha.getText());
        if (erros.isEmpty()) {
            progressIndicator.setVisible(true);
            new Login().start();
        } else {
            setErroFormLoginValidator(erros);
        }
    }

    //TODO: Verificar progress bar
    public class Login extends Thread {
        @Override
        public void run() {
            progressIndicator.setVisible(true);
            disableButtonAndTextField();
            cred.setEmail(txtEmail.getText());
            cred.setSenha(txtSenha.getText());
            response.clear();
            response = usuarioService.logIn(cred);
            progressIndicator.setVisible(false);
            if (response.containsKey(Constantes.STATUS_CODE_SUCCESSFUL)) {
                //popular objeto usuario e enviar para a tela principal
                Platform.runLater(() -> {
                    dao.saveToken(response.get(Constantes.STATUS_CODE_SUCCESSFUL));
                    user = usuarioService.getUserLogged(response.get(Constantes.STATUS_CODE_SUCCESSFUL));
                    loadNewViewAndCloseOld("/view/MainScreen.fxml", btnlogin, user);
                });
            } else {
                Platform.runLater(() -> {
                    //exibir error para o usuario
                    enableButtonAndTextField();
                    setResponseHttp(response);
                });
            }

        }
    }

    @FXML
    public void loadNewViewAndCloseOld(String caminho, JFXButton componente, UsuarioModel user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(caminho));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            ControllerMainScreen tela = fxmlLoader.getController();
            tela.initializeInfoUser(user);

            scene.setFill(Color.TRANSPARENT);
            stage.getIcons().add(new Image("icons/icon_maracuja_64px.png"));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

            if (componente != null) {
                //a partir do componenete de layout recupero a janela a ser fechada
                Stage stage2 = (Stage) componente.getScene().getWindow();
                stage2.close();
            }

            scene.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            scene.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
                stage.setOpacity(0.7);
            });

            scene.setOnMouseReleased(mouseEvent -> stage.setOpacity(1));
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
            stage.getIcons().add(new Image("icons/icon_maracuja_64px.png"));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

            if (componente != null) {
                //a partir do componenete de layout recupero a janela a ser fechada
                Stage stage2 = (Stage) componente.getScene().getWindow();
                stage2.close();
            }

            scene.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            scene.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
                stage.setOpacity(0.7);
            });

            scene.setOnMouseReleased(mouseEvent -> stage.setOpacity(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setResponseHttp(Map<Integer, String> responseHttp) {
        Set<Integer> codeResponse = responseHttp.keySet();
        Alert alerta = new Alert(Alert.AlertType.ERROR);

        alerta.setTitle(ValidatorExceptionsMessage.FORM_LOGIN_TITLE_ALERT);
        alerta.setHeaderText(ValidatorExceptionsMessage.FORM_LOGIN_HEADER_ALERT);

        if (codeResponse.contains(Constantes.STATUS_CODE_UNAUTHORIZED)) {
            alerta.setContentText(responseHttp.get(Constantes.STATUS_CODE_UNAUTHORIZED));
            alerta.showAndWait();
        } else if (codeResponse.contains(Constantes.STATUS_CODE_INTERNAL_SERVER_ERROR)) {
            alerta.setContentText(responseHttp.get(Constantes.STATUS_CODE_INTERNAL_SERVER_ERROR));
            alerta.showAndWait();
        } else if (codeResponse.contains(Constantes.STATUS_CODE_SERVICE_UNAVAILABLE)) {
            alerta.setContentText(responseHttp.get(Constantes.STATUS_CODE_SERVICE_UNAVAILABLE));
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

    private void disableButtonAndTextField() {
        txtEmail.setDisable(true);
        txtSenha.setDisable(true);
        btnlogin.setDisable(true);
    }

    private void enableButtonAndTextField() {
        txtEmail.setDisable(false);
        txtSenha.setDisable(false);
        btnlogin.setDisable(false);
    }
    @FXML
    private void keyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    @FXML
    private void encerrarAplicacao() {
        Platform.exit();
    }

}
