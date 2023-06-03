module com.example.nuclearthrone {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.media;

    opens com.example.nuclearthrone to javafx.fxml;
    exports com.example.nuclearthrone;
    opens com.example.nuclearthrone.model to javafx.fxml;
    exports com.example.nuclearthrone.model;
    opens com.example.nuclearthrone.model.menus to javafx.fxml;
    exports com.example.nuclearthrone.model.menus;
    opens com.example.nuclearthrone.model.level to javafx.fxml;
    exports com.example.nuclearthrone.model.level;
    opens com.example.nuclearthrone.model.entity to javafx.fxml;
    exports com.example.nuclearthrone.model.entity;
    opens com.example.nuclearthrone.model.item to javafx.fxml;
    exports com.example.nuclearthrone.model.item;
}
