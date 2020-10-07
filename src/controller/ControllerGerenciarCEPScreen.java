package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;
import model.EnderecoModel;
import service.EnderecoService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerGerenciarCEPScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private JFXTreeTableView<Endereco> treeView;

    @FXML
    private Button btnSair;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    class Endereco extends RecursiveTreeObject<Endereco> {
        StringProperty cep;
        StringProperty logradouro;
        StringProperty bairro;

        public Endereco(String cep, String logradouro, String bairro) {
            this.cep = new SimpleStringProperty(cep);
            this.logradouro = new SimpleStringProperty(logradouro);
            this.bairro = new SimpleStringProperty(bairro);
        }
    }

    public void sair() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }
}
