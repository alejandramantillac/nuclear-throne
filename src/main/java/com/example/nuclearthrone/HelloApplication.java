package com.example.nuclearthrone;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    static double width;
    static double height;
    @Override
    public void start(Stage stage) throws IOException {
        width = stage.getWidth();
        height = stage.getHeight();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static File getFile(String fileName) {
        return new File(HelloApplication.class.getResource(fileName).getPath());
    }

    public static void main(String[] args) {
        launch();
    }

    public static double getWidth(){
        return width;
    }

    public static double getHeight(){
        return height;
    }

    public static int msRate(){
        return 16;
    }
}