package controller;

import animatefx.animation.FadeIn;
import animatefx.animation.Pulse;
import animatefx.animation.SlideInLeft;
import dao.EnderecoDAO;
import helper.ViaCEPException;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import model.EnderecoModel;
import service.ViaCEP;
import util.MaskField;
import validator.Validation;

import java.util.Optional;


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

    @FXML
    private void telaNovoCEP(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Novo CEP");
        dialog.setHeaderText("Adicione um novo CEP");

        ButtonType loginButtonType = new ButtonType("Adicionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        alertCEP = new MaskField();
        alertCEP.setMask("DDDDDDDD");

        alertBairro = new TextField();
        alertBairro.setDisable(true);

        alertLogradouro = new TextField();
        alertLogradouro.setDisable(true);

        popularCampos();

        grid.add(new Label("CEP:"), 0, 0);
        grid.add(alertCEP, 1, 0);

        grid.add(new Label("Bairro:"), 0, 1);
        grid.add(alertBairro, 1, 1);

        grid.add(new Label("Logradouro:"), 0, 2);
        grid.add(alertLogradouro, 1, 2);

        Node btnAdicionar = dialog.getDialogPane().lookupButton(loginButtonType);
        btnAdicionar.setDisable(true);

        btnAdicionar.setDisable(!val.verificarCEP(txtCEP.getText()));

        alertCEP.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:
                        try {
                            viaCEP.buscar(alertCEP.getText());
                            alertLogradouro.setText(viaCEP.getLogradouro());
                            alertLogradouro.setText(viaCEP.getBairro());
                            btnAdicionar.setDisable(false);
                        } catch (ViaCEPException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("CEP Invalido");
                            alert.setHeaderText(null);
                            alert.setContentText("Nao foi possivel encontrar o CEP");
                            alert.show();
                        }
                        break;
                    case BACK_SPACE:
                        alertCEP.textProperty().addListener((observable, oldValue, newValue) -> {
                            System.out.println(newValue);
                            btnAdicionar.setDisable(!val.verificarCEP(newValue));
                        });
                        break;
                }
            }
        });

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> alertCEP.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(alertCEP.getText(), alertLogradouro.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {

            EnderecoModel endereco = dao.consultarCEP(alertCEP.getText());

            if (endereco == null){
                endereco = new EnderecoModel();
                endereco.setCEP(alertCEP.getText());
                endereco.setLogradouro(alertLogradouro.getText());
                endereco.setBairro(alertBairro.getText());
                dao.inserirNovoEndereco(endereco);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("CEP EXISTENTE");
                alert.setHeaderText(null);
                alert.setContentText("CEP ja consta na base de dados");
                alert.showAndWait();
            }
        });
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