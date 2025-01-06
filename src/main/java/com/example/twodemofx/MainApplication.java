package com.example.twodemofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 695, 580);
        stage.setResizable(false);
        stage.setTitle("MyApp");
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        MainController mainController = fxmlLoader.getController();
        mainController.setStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}