package controller;

import animatefx.animation.SlideInLeft;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.EnderecoService;
import util.MaskField;

import java.io.*;
import java.net.URL;
import java.util.*;

import static org.apache.commons.collections4.IteratorUtils.toList;

public class ControllerMainScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    ProgressIndicator progressIndicator;

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

    int excelColumnsNumber = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeListViewMainMenu();
    }

    public void clickItemList() {
        switch (listViewMainMenu.getSelectionModel().getSelectedItem().getText()) {
            case "Importar Excel":
                new importCSV().start();
                break;

            case "Gerenciar CEP's":
                loadNewViewAndCloseOld("/view/GerenciarCEPScreen.fxml", null);
                break;

            case "Configurações":
                System.out.println("Configurações");
                break;
        }
    }

    @FXML
    private void searchCEP(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ENTER) {
            EnderecoModel endereco = enderecoService.getCepApiMaracuja(txtCEP.getText());
            if (endereco != null) {
                txtLougradouro.setText(endereco.getLogradouro());
                txtBairro.setText(endereco.getBairro());
                paneResposta.setVisible(true);
                txtResultado.setText("Com Estrutura");
                paneResposta.setStyle("-fx-background-color: #86C60F; -fx-background-radius: 0 0 18 0;");
                new SlideInLeft(paneResposta).play();
            } else {
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

    public void initializeListViewMainMenu() {
        try {
            Map<String, String> itemList = new HashMap<>();
            itemList.put("Importar Excel", "C:\\Users\\renna\\IdeaProjects\\maracuja\\src\\icons\\mainmenu\\icon_import_csv.png");
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

            scene.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            scene.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
                stage.setOpacity(0.7);
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

    public class importCSV extends Thread {
        @Override
        public void run() {
            Platform.runLater(() -> {
                FileChooser fl = new FileChooser();
                fl.setTitle("Selecione um arquivo");
                fl.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xlsx Files", "*.xlsx"));
                File selecteFile = fl.showOpenDialog(null);

                try {
                    Workbook workbook = new XSSFWorkbook(selecteFile);
                    Sheet abaPlanilha = workbook.getSheetAt(0);

                    List<Row> linhas = (List<Row>) toList(abaPlanilha.iterator());

                    linhas.forEach(linha -> {
                        List<?> celulas = toList(linha.cellIterator());
                        excelColumnsNumber = celulas.size();
                    });

                    List<String> choices = new ArrayList<>();
                    for (int i = 1; i <= excelColumnsNumber; i++) {
                        choices.add(String.valueOf(i));
                    }

                    ChoiceDialog<String> dialog = new ChoiceDialog<>("1", choices);
                    dialog.setTitle("Selecione a coluna");
                    dialog.setHeaderText("Em qual coluna se localiza os CEP's?");
                    dialog.setContentText("Escolha a coluna:");
                    Optional<String> result = dialog.showAndWait();

                    if (result.isPresent()) {
                        progressIndicator.setVisible(true);
                        linhas.forEach(linha -> {
                            List<?> celulas = toList(linha.cellIterator());
                            System.out.println(celulas.get(Integer.parseInt(result.get()) - 1));
                        });
                    } else {
                        progressIndicator.setVisible(false);
                    }

                } catch (IOException | InvalidFormatException e) {
                    e.printStackTrace();
                }
                progressIndicator.setVisible(false);
            });
        }
    }

    @FXML
    private void encerrarAplicacao() {
        Platform.exit();
    }
}