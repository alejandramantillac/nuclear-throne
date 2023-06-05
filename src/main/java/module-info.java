module com.example.nuclearthrone {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive javafx.media;

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
    opens com.example.nuclearthrone.model.entity.item to javafx.fxml;
    exports com.example.nuclearthrone.model.entity.item;
    exports com.example.nuclearthrone.model.entity.enemy;
    opens com.example.nuclearthrone.model.entity.enemy to javafx.fxml;
    exports com.example.nuclearthrone.model.entity.enviroment;
    opens com.example.nuclearthrone.model.entity.enviroment to javafx.fxml;
    exports com.example.nuclearthrone.model.entity.ammo;
    opens com.example.nuclearthrone.model.entity.ammo to javafx.fxml;
    opens com.example.nuclearthrone.model.util to javafx.fxml;
    exports com.example.nuclearthrone.model.util;
}
