module com.example.nuclearthrone {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.media;

    opens com.example.nuclearthrone to javafx.fxml;
    exports com.example.nuclearthrone;
    opens com.example.nuclearthrone.model to javafx.fxml;
    exports com.example.nuclearthrone.model;
}