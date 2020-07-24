package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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

import java.io.IOException;

public class ControllerFXMLTelaLogin {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void encerrarAplicacao(){
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
        if (txtEmail.getText().equals("r@r.com") && txtSenha.getText().equals("rammstein-1")) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainScreen.fxml"));
                Parent parent = fxmlLoader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                scene.setFill(Color.TRANSPARENT);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();

                Stage stage2 = (Stage) btnlogin.getScene().getWindow();
                stage2.close();

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
        return true;
    }

    @FXML
    public void register() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/RegisterScreen.fxml"));
            Parent parent = null;
            parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

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
