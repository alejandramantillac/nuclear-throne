package com.example.nuclearthrone;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class PlayAgain extends Application {

    @FXML
    private ImageView image;

    @FXML
    private Button menuBtn;

    @FXML
    private ImageView playAgainBtn;

    @FXML
    private AnchorPane root;

    private Canvas canvas;

    public static void openWindow(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getView(fxml));
            AnchorPane root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Nuclear Throne");
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void start(Stage stg) throws IOException {
        stg.setTitle("Play Again");

        // Load fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getView("play-again"));
        AnchorPane root = fxmlLoader.load();

        // Play song
        //playSoundtrack.mainMenuSong();

        // Background with gradient
        Rectangle background = new Rectangle(800, 600);
        RadialGradient gradient = new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(51, 0, 102, 0.35)),
                new Stop(1, Color.rgb(17, 17, 34, 0.35)));
        background.setFill(gradient);

        // Ball Movement 1
        canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Color ballColor = Color.rgb(181, 126, 220);
        gc.setFill(ballColor);
        double ballX = 50;
        double ballY = 50;
        double ballRadius = 50;
        Circle ball = new Circle(ballX, ballY, ballRadius, ballColor);

        // Transition ball 1
        TranslateTransition ballTransition = new TranslateTransition(Duration.seconds(10), ball);
        ballTransition.setToX(750);
        ballTransition.setToY(550);
        ballTransition.setCycleCount(Animation.INDEFINITE);
        ballTransition.setAutoReverse(true);
        ballTransition.play();

        root.getChildren().add(ball);

        // Ball Movement 2
        canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        Color ballColor1 = Color.rgb(181, 126, 220);
        gc.setFill(ballColor1);
        ballRadius = 50;
        Circle ball1 = new Circle(ballRadius, ballColor1);

        double maxX = canvas.getWidth() - ballRadius * 2;
        double maxY = canvas.getHeight() - ballRadius * 2;

        // Transition ball 2
        TranslateTransition ballTransition1 = new TranslateTransition(Duration.seconds(10), ball1);
        ballTransition1.setFromX(maxX);
        ballTransition1.setToX(ballRadius);
        ballTransition1.setFromY(maxY);
        ballTransition1.setToY(ballRadius);
        ballTransition1.setCycleCount(Animation.INDEFINITE);
        ballTransition1.setAutoReverse(true);
        ballTransition1.setInterpolator(Interpolator.LINEAR);
        ballTransition1.play();

        root.getChildren().add(ball1);

        // Buttons references
        menuBtn = (Button) fxmlLoader.getNamespace().get("menuBtn");
        playAgainBtn = (ImageView) fxmlLoader.getNamespace().get("playAgainBtn");

        menuBtn.setOnAction(e -> {
            openWindow("main-menu");
        });

        playAgainBtn.setOnMouseClicked(e -> {
            openWindow("game");
        });


        // Buttons animation
        RotateTransition rtPlay = new RotateTransition(Duration.seconds(3), menuBtn);
        rtPlay.setByAngle(5);
        rtPlay.setAutoReverse(true);
        rtPlay.setCycleCount(RotateTransition.INDEFINITE);
        rtPlay.play();

        RotateTransition rtSettings = new RotateTransition(Duration.seconds(3), playAgainBtn);
        rtSettings.setByAngle(5);
        rtSettings.setAutoReverse(true);
        rtSettings.setCycleCount(RotateTransition.INDEFINITE);
        rtSettings.setDelay(Duration.seconds(0.5));
        rtSettings.play();


        HBox hBox = new HBox(20, menuBtn, playAgainBtn);
        hBox.setAlignment(Pos.CENTER);
        hBox.setTranslateX(260);
        hBox.setTranslateY(200);

        root.getChildren().addAll(background, hBox);

        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();
    }

    public static URL getView(String name) {
        return MainMenu.class.getResource("windows/" + name + ".fxml");
    }
}
