package controller;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.EnderecoModel;
import model.UsuarioModel;
import service.EnderecoService;
import util.MaskField;

import java.io.*;
import java.net.URL;
import java.util.*;

public class ControllerMainScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

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
        switch (listViewMainMenu.getSelectionModel().getSelectedItem().getText()) {
            case "Importar CSV":
                System.out.println("Importar CSV");
                System.out.println();
                importCSV();
                break;

            case "Gerenciar CEP's":
                loadNewViewAndCloseOld("/view/GerenciarCEPScreen.fxml",null);
                break;

            case "Configurações":
                System.out.println("Configurações");
                break;
        }
    }

    @FXML
    private void searchCEP(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ENTER) {
            EnderecoModel endereco =  enderecoService.getCepApiMaracuja(txtCEP.getText());
            if (endereco != null){
                txtLougradouro.setText(endereco.getLogradouro());
                txtBairro.setText(endereco.getBairro());
                paneResposta.setVisible(true);
                txtResultado.setText("Com Estrutura");
                paneResposta.setStyle("-fx-background-color: #86C60F; -fx-background-radius: 0 0 18 0;");
                new SlideInLeft(paneResposta).play();
            }else {
                endereco = enderecoService.getCepViaCep(txtCEP.getText());
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
            enderecoService.addNewCep(ceps);
        }

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

    public void loadNewViewAndCloseOld(String caminho, JFXButton componente) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(caminho));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

            if (componente != null) {
                //a partir do componenete de layout recupero a janela a ser fechada
                Stage stage2 = (Stage) componente.getScene().getWindow();
                stage2.close();
            }

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

    @FXML
    private void encerrarAplicacao() {
        Platform.exit();
    }
}