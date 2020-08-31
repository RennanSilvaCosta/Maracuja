package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.CredenciaisDTO;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.UsuarioService;

import java.io.IOException;

public class ControllerFXMLTelaLogin {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void encerrarAplicacao() {
        Platform.exit();
    }

    @FXML
    JFXTextField txtEmail;

    @FXML
    JFXPasswordField txtSenha;

    @FXML
    JFXButton btnlogin = new JFXButton();

    @FXML
    private boolean login() {

        CredenciaisDTO cred = new CredenciaisDTO();
        UsuarioService usuarioService = new UsuarioService();

        cred.setEmail(txtEmail.getText());
        cred.setSenha(txtSenha.getText());
        usuarioService.efetuarLogin(cred);

        /*
        btnlogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loadNewViewAndCloseOld("/view/MainScreen.fxml", btnlogin);
            }
        });*/

        return true;
    }

    @FXML
    public void register() {
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
                //a partir do componenete recupero a janela a ser fechada
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
}
