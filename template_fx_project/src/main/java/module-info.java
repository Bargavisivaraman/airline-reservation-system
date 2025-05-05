module registration.template {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens registration.template to javafx.fxml;
    exports registration.template;
}
