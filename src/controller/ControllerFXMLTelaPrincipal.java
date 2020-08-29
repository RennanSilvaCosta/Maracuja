package controller;

import animatefx.animation.Pulse;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import util.MaskField;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeListViewMainMenu();
    }

    public void clickItemList() {

        switch (listViewMainMenu.getSelectionModel().getSelectedIndex()) {
            case 0:
                System.out.println("Importa CSV");
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

        switch (ke.getCode()) {
            case ENTER:
                System.out.println("Buscar CEP");

        }
    }

    public void importCSV(){
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeListViewMainMenu() {
        try {
            itemListView = new Label("Importar CSV");
            itemListView.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\maracuja\\src\\icons\\mainmenu\\icon_import_csv.png"))));
            itemListView.setGraphicTextGap(20);
            listViewMainMenu.getItems().add(itemListView);

            itemListView = new Label("Gerenciar CEP's");
            itemListView.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\maracuja\\src\\icons\\mainmenu\\icon_folder.png"))));
            itemListView.setGraphicTextGap(20);
            listViewMainMenu.getItems().add(itemListView);

            itemListView = new Label("Configurações");
            itemListView.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\renna\\IdeaProjects\\maracuja\\src\\icons\\mainmenu\\icon_gear.png"))));
            itemListView.setGraphicTextGap(20);
            listViewMainMenu.getItems().add(itemListView);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void encerrarAplicacao(){
        Platform.exit();
    }
}