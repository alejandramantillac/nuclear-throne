package com.example.nuclearthrone;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static double width;
    private static double height;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setWidth(1280);
        stage.setHeight(720);
        init(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),width,height);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.resizableProperty().set(false);
        stage.show();
    }
    
    public static void init(Stage stage) {
        width = stage.getWidth();
        height = stage.getHeight();
    }

    public static File getFile(String fileName) {
        return new File(App.class.getResource(fileName).getPath());
    }

    public static void main(String[] args) {
        launch();
    }

    public static double getWidth() {
        return width;
    }

    public static double getHeight() {
        return height;
    }

    public static int msRate() {
        return 16;
    }
}