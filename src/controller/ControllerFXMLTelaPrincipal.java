package controller;

import animatefx.animation.Pulse;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import util.MaskField;

import java.io.*;

public class ControllerFXMLTelaPrincipal {

    @FXML
    Hyperlink addCEP = new Hyperlink();

    @FXML
    MaskField txtCEP = new MaskField();

    @FXML
    TextField txtLougradouro, txtBairro;

    @FXML
    Pane paneColor, paneIcon, paneResposta, paneAddNewCep;

    @FXML
    Button btnSair;

    @FXML
    Label txtResultado;

    @FXML
    private void encerrarAplicacao(){
        Platform.exit();
    }

    @FXML
    private void searchCEP(KeyEvent ke) {

        switch (ke.getCode()) {
            case ENTER:
                new Pulse(paneIcon).setSpeed(0.6).play();
        }
    }

    public void importarCSB(){
        FileChooser fl = new FileChooser();
        fl.setTitle("Selecione um arquivo");
        fl.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File selecteFile = fl.showOpenDialog(null);
        String patch = selecteFile.getPath();

        try  (BufferedReader br = new BufferedReader(new FileReader(patch))){
            String line = br.readLine();
            while (line != null){
                System.out.println(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}