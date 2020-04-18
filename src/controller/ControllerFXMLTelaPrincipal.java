package controller;

import animatefx.animation.FadeIn;
import animatefx.animation.Pulse;
import animatefx.animation.SlideInLeft;
import dao.EnderecoDAO;
import helper.ViaCEPException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.EnderecoModel;
import service.ViaCEP;
import util.MaskField;
import validator.Validation;


public class ControllerFXMLTelaPrincipal {

    @FXML
    MaskField txtCEP = new MaskField();

    @FXML
    TextField txtLougradouro, txtBairro;

    @FXML
    Pane paneColor, paneIcon, paneResposta;

    @FXML
    Button btnSair, btnNovoCEP;

    @FXML
    Label txtResultado;

    TextField alertLogradouro, alertBairro;

    MaskField alertCEP;

    Validation val = new Validation();
    ViaCEP viaCEP = new ViaCEP();
    EnderecoModel end = new EnderecoModel();
    EnderecoDAO dao = new EnderecoDAO();

    @FXML
    private void encerrarAplicacao(){
        Platform.exit();
    }

    @FXML
    private void searchCEP(KeyEvent ke) {

        switch (ke.getCode()) {
            case ENTER:

                new Pulse(paneIcon).setSpeed(0.6).play();
                end = dao.consultarCEP(txtCEP.getText());

                if (end == null){
                    ViaCEP viaCEP = new ViaCEP();
                    try {
                        viaCEP.buscar(txtCEP.getText());
                        paneResposta.setVisible(true);
                        paneResposta.setStyle("-fx-background-color: #FD1810; -fx-background-radius: 0 0 18 0;");
                        new FadeIn(paneResposta).play();
                        new SlideInLeft(paneResposta).play();
                        txtResultado.setText("SEM ESTRUTURA");

                        txtLougradouro.setText(viaCEP.getLogradouro());
                        txtBairro.setText(viaCEP.getBairro());

                    } catch (ViaCEPException e) {
                        paneResposta.setStyle("-fx-background-color: #FD1810; -fx-background-radius: 0 0 18 0;");
                        new FadeIn(paneResposta).play();
                        new SlideInLeft(paneResposta).play();
                        paneResposta.setVisible(true);
                        txtResultado.setText("CEP INVALIDO");

                        txtLougradouro.setText("");
                        txtBairro.setText("");
                    }

                } else {
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

    private void popularCampos(){
        if(val.verificarCEP(txtCEP.getText())){
            try {
                viaCEP.buscar(txtCEP.getText());
                alertCEP.setText(txtCEP.getText());
                alertBairro.setText(viaCEP.getBairro());
                alertLogradouro.setText(viaCEP.getLogradouro());
            } catch (ViaCEPException e) {
                e.printStackTrace();
            }
        } else {
            alertBairro.setText("Bairro");
            alertLogradouro.setText("Logradouro");
        }
    }
}