package controller;

import animatefx.animation.*;
import helper.ViaCEPException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.EnderecoModel;
import service.ViaCEP;
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

    EnderecoModel end = new EnderecoModel();

    @FXML
    private void encerrarAplicacao(){
        Platform.exit();
    }

    @FXML
    private void searchCEP(KeyEvent ke) {

        switch (ke.getCode()) {
            case ENTER:
                new Pulse(paneIcon).setSpeed(0.6).play();
                end = null;
                if (end == null){
                    ViaCEP viaCEP = new ViaCEP();
                    try {
                        viaCEP.buscar(txtCEP.getText());
                        paneResposta.setVisible(true);
                        paneResposta.setStyle("-fx-background-color: #FD1810; -fx-background-radius: 0 0 18 0;");
                        paneAddNewCep.setVisible(true);
                        new SlideOutUp(paneAddNewCep).play();
                        new FadeIn(paneResposta).play();
                        new SlideInLeft(paneResposta).play();
                        txtResultado.setText("SEM ESTRUTURA");
                        txtLougradouro.setText(viaCEP.getLogradouro());
                        txtBairro.setText(viaCEP.getBairro());
                    } catch (ViaCEPException e) {
                        if (paneAddNewCep.isVisible()){
                            paneAddNewCep.setVisible(false);
                        }
                        paneResposta.setStyle("-fx-background-color: #FD1810; -fx-background-radius: 0 0 18 0;");
                        new FadeIn(paneResposta).play();
                        new SlideInLeft(paneResposta).play();
                        paneResposta.setVisible(true);
                        txtResultado.setText("CEP INVALIDO");

                        txtLougradouro.setText("");
                        txtBairro.setText("");
                    }
                } else {
                    if (paneAddNewCep.isVisible()){
                        paneAddNewCep.setVisible(false);
                    }
                    paneResposta.setVisible(true);
                    paneResposta.setStyle("-fx-background-color: #86C60F; -fx-background-radius: 0 0 18 0;");
                    new FadeIn(paneResposta).play();
                    new SlideInLeft(paneResposta).play();
                    txtResultado.setText("COM ESTRUTURA");

                    txtLougradouro.setText(end.getLogradouro());
                    txtBairro.setText(end.getBairro());
                }
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