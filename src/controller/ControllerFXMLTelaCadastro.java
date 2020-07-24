package controller;


import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ControllerFXMLTelaCadastro {

    @FXML
    JFXButton btnSair;

    public void sair() {

        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();

    }
}
