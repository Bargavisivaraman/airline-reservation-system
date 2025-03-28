module registration.template {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens registration.template to javafx.fxml;
    exports registration.template;
}
