package controller;

import animatefx.animation.SlideInLeft;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.NewEnderecoDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.EnderecoModel;
import service.EnderecoService;
import util.Constantes;
import util.MaskField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerGerenciarCEPScreen implements Initializable {

    @FXML
    private JFXTreeTableView<Endereco> treeView;

    @FXML
    private JFXButton btnSair;

    @FXML
    MaskField txtCep;

    @FXML
    Label lblError;

    private EnderecoService enderecoService = new EnderecoService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
    }

    private void initializeTable() {
        JFXTreeTableColumn<Endereco, String> cepColumn = new JFXTreeTableColumn<>("CEP");
        cepColumn.setPrefWidth(100);
        cepColumn.setMaxWidth(100);
        cepColumn.setMinWidth(100);
        cepColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Endereco, String> param) -> {
            if (cepColumn.validateValue(param)) return param.getValue().getValue().cep;
            else return cepColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<Endereco, String> logradouroColumn = new JFXTreeTableColumn<>("Logradouro");
        logradouroColumn.setPrefWidth(350);
        logradouroColumn.setMaxWidth(350);
        logradouroColumn.setMinWidth(250);
        logradouroColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Endereco, String> param) -> {
            if (logradouroColumn.validateValue(param)) return param.getValue().getValue().logradouro;
            else return logradouroColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<Endereco, String> bairroColumn = new JFXTreeTableColumn<>("Bairro");
        bairroColumn.setPrefWidth(250);
        bairroColumn.setMaxWidth(250);
        bairroColumn.setMinWidth(200);
        bairroColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Endereco, String> param) -> {
            if (bairroColumn.validateValue(param)) return param.getValue().getValue().bairro;
            else return bairroColumn.getComputedValue(param);
        });

        ObservableList<Endereco> end = FXCollections.observableArrayList();

        EnderecoService enderecoService = new EnderecoService();
        List<EnderecoModel> endereco;
        endereco = enderecoService.getAll();

        if (endereco != null) {
            for (EnderecoModel enderecoModel : endereco) {
                end.add(new Endereco(enderecoModel.getCep(), enderecoModel.getLogradouro(), enderecoModel.getBairro()));
            }
        }

        final TreeItem<Endereco> root = new RecursiveTreeItem<Endereco>(end, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(cepColumn, logradouroColumn, bairroColumn);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }

    static class Endereco extends RecursiveTreeObject<Endereco> {
        StringProperty cep;
        StringProperty logradouro;
        StringProperty bairro;

        public Endereco(String cep, String logradouro, String bairro) {
            this.cep = new SimpleStringProperty(cep);
            this.logradouro = new SimpleStringProperty(logradouro);
            this.bairro = new SimpleStringProperty(bairro);
        }
    }

    @FXML
    private void searchCEP(KeyEvent ke) {
        lblError.setText("");
        if (ke.getCode() == KeyCode.ENTER) {
            EnderecoModel endereco = enderecoService.getCepViaCep(txtCep.getText());
            NewEnderecoDTO end = new NewEnderecoDTO(endereco);
            if (endereco != null) {
                enderecoService.saveCep(end);
            } else {
                lblError.setText("O cep informado é inválido.");
            }
        }
    }

    public void sair() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }
}
