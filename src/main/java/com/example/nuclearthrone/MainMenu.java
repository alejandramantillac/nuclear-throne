package com.example.nuclearthrone;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import com.example.nuclearthrone.model.entity.Avatar;
import com.example.nuclearthrone.model.level.Level;
import com.example.nuclearthrone.model.menus.Soundtrack;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenu extends Application {
    
    static Stage gameStage;

    @FXML
    private Button playButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button creditsButton;

    @FXML
    private Button quitButton;

    @FXML
    private ImageView logoImage;

    public static void main(String[] args) {
        launch(args);
    }

    private Canvas canvas;

    Soundtrack playSoundtrack = Soundtrack.getInstance();

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Main Menu");

        // Load fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getView("main-menu"));
        AnchorPane root = fxmlLoader.load();

        // Play song
        playSoundtrack.mainMenuSong();

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
        playButton = (Button) fxmlLoader.getNamespace().get("playButton");
        settingsButton = (Button) fxmlLoader.getNamespace().get("settingsButton");
        creditsButton = (Button) fxmlLoader.getNamespace().get("creditsButton");
        quitButton = (Button) fxmlLoader.getNamespace().get("quitButton");

        playButton.setOnAction(e -> {
            initGame();
        });

        settingsButton.setOnAction(e -> {
            openWindow("settings");
        });

        creditsButton.setOnAction(e -> {
            openWindow("credits");
        });

        quitButton.setOnAction(e -> {
            primaryStage.close();
        });

        // Buttons animation
        RotateTransition rtPlay = new RotateTransition(Duration.seconds(3), playButton);
        rtPlay.setByAngle(5);
        rtPlay.setAutoReverse(true);
        rtPlay.setCycleCount(RotateTransition.INDEFINITE);
        rtPlay.play();

        RotateTransition rtSettings = new RotateTransition(Duration.seconds(3), settingsButton);
        rtSettings.setByAngle(5);
        rtSettings.setAutoReverse(true);
        rtSettings.setCycleCount(RotateTransition.INDEFINITE);
        rtSettings.setDelay(Duration.seconds(0.5));
        rtSettings.play();

        RotateTransition rtCredits = new RotateTransition(Duration.seconds(3), creditsButton);
        rtCredits.setByAngle(5);
        rtCredits.setAutoReverse(true);
        rtCredits.setCycleCount(RotateTransition.INDEFINITE);
        rtCredits.setDelay(Duration.seconds(1));
        rtCredits.play();

        RotateTransition rtQuit = new RotateTransition(Duration.seconds(3), quitButton);
        rtQuit.setByAngle(5);
        rtQuit.setAutoReverse(true);
        rtQuit.setCycleCount(RotateTransition.INDEFINITE);
        rtQuit.setDelay(Duration.seconds(1.5));
        rtQuit.play();

        VBox vBox = new VBox(20, playButton, settingsButton, creditsButton, quitButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setTranslateX(260);
        vBox.setTranslateY(200);

        root.getChildren().addAll(background, vBox);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.onCloseRequestProperty().set(event->{
            Soundtrack.getInstance().getMediaPlayer().pause();
        });
    }

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

    
    public static void initGame() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getView("game"));
            Scene scene = new Scene(fxmlLoader.load(), getWidth(), getHeight());

            ImageCursor cursor = new ImageCursor(new Image(getFile("cursor.png").getPath()),30,30);
            scene.setCursor(cursor);

            if(gameStage == null){
                gameStage = new Stage();
                gameStage.setWidth(1280);
                gameStage.setHeight(720);
                gameStage.setTitle("Nuclear Throne");
                gameStage.resizableProperty().set(false);
            }
            
            gameStage.setScene(scene);
            gameStage.show();
            gameStage.setOnCloseRequest(event->{
                Avatar.resetAvatar();
                Level.resetLevels();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static URL getView(String name) {
        return MainMenu.class.getResource("windows/" + name + ".fxml");
    }

    public static File getFile(String fileName) {
        return new File(Objects.requireNonNull(MainMenu.class.getResource(fileName)).getPath());
    }

    public static double getWidth() {
        return 1280;
    }

    public static double getHeight() {
        return 720;
    }

    public static int msRate() {
        return 16;
    }
}
