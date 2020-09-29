package controller;

import animatefx.animation.*;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.EnderecoModel;
import model.UsuarioModel;
import service.EnderecoService;
import util.MaskField;

import java.io.*;
import java.net.URL;
import java.util.*;

public class ControllerFXMLTelaPrincipal implements Initializable {

    @FXML
    Hyperlink addCEP = new Hyperlink();

    @FXML
    MaskField txtCEP = new MaskField();

    @FXML
    TextField txtLougradouro, txtBairro;

    @FXML
    Pane paneColor, paneResposta, paneAddNewCep;

    @FXML
    Button btnSair;

    @FXML
    Label txtResultado, txtNomeUsuario, txtEmailUsuario, txtNomeEmpresa;

    @FXML
    JFXListView<Label> listViewMainMenu = new JFXListView<>();

    Label itemListView;

    EnderecoService enderecoService = new EnderecoService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeListViewMainMenu();
    }

    public void clickItemList() {
        switch (listViewMainMenu.getSelectionModel().getSelectedIndex()) {
            case 0:
                System.out.println("Importa CSV");
                importCSV();
                break;

            case 1:
                System.out.println("Gerenciar CEP's");
                break;

            case 2:
                System.out.println("Configurações");
                break;
        }
    }

    @FXML
    private void searchCEP(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ENTER) {
            EnderecoModel endereco =  enderecoService.searchCepApiMaracuja(txtCEP.getText());
            if (endereco != null){
                txtLougradouro.setText(endereco.getLogradouro());
                txtBairro.setText(endereco.getBairro());
                paneResposta.setVisible(true);
                txtResultado.setText("Com Estrutura");
                paneResposta.setStyle("-fx-background-color: #86C60F; -fx-background-radius: 0 0 18 0;");
                new SlideInLeft(paneResposta).play();
            }else {
                endereco = enderecoService.searchCepWithViaCep(txtCEP.getText());
                txtLougradouro.setText(endereco.getLogradouro());
                txtBairro.setText(endereco.getBairro());
                paneResposta.setVisible(true);
                txtResultado.setText("Sem estrutura");
                paneResposta.setStyle("-fx-background-color: #FD1810; -fx-background-radius: 0 0 18 0;");
                new SlideInLeft(paneResposta).play();
            }
        }
    }

    public void importCSV() {
        FileChooser fl = new FileChooser();
        fl.setTitle("Selecione um arquivo");
        fl.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selecteFile = fl.showOpenDialog(null);
        List<String> ceps = new ArrayList<>();

        if (selecteFile != null) {
            String patch = selecteFile.getPath();
            try (BufferedReader br = new BufferedReader(new FileReader(patch))) {
                String line = br.readLine();
                while (line != null) {
                    ceps.add(line);
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        enderecoService.addNewCep(ceps);
    }

    public void initializeListViewMainMenu() {
        try {
            Map<String, String> itemList = new HashMap<>();
            itemList.put("Importar CSV", "C:\\Users\\renna\\IdeaProjects\\maracuja\\src\\icons\\mainmenu\\icon_import_csv.png");
            itemList.put("Gerenciar CEP's", "C:\\Users\\renna\\IdeaProjects\\maracuja\\src\\icons\\mainmenu\\icon_folder.png");
            itemList.put("Configurações", "C:\\Users\\renna\\IdeaProjects\\maracuja\\src\\icons\\mainmenu\\icon_gear.png");

            for (String labelsItemList : itemList.keySet()) {
                itemListView = new Label(labelsItemList);
                itemListView.setGraphic(new ImageView(new Image(new FileInputStream(itemList.get(labelsItemList)))));
                itemListView.setGraphicTextGap(20);
                listViewMainMenu.getItems().add(itemListView);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initializeInfoUser(UsuarioModel user) {
        txtEmailUsuario.setText(user.getEmail());
        txtNomeUsuario.setText(user.getNome());
        txtNomeEmpresa.setText(user.getEmpresa().getNome());
    }

    @FXML
    private void encerrarAplicacao() {
        Platform.exit();
    }
}