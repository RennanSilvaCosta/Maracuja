package main;

import dao.UsuarioDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    UsuarioDAO dao = new UsuarioDAO();

    @Override
    public void start(Stage primaryStage) throws Exception {
        dao.createTable();
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(new Image("icons\\icon_maracuja_64px.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getRoot().requestFocus();

        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
            primaryStage.setOpacity(0.7);
        });
        scene.setOnMouseReleased(mouseEvent -> primaryStage.setOpacity(1));
    }

    public static void main(String[] args) {
        launch(args);
    }
}