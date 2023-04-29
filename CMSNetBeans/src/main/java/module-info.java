module CMS {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens CMS to javafx.fxml;
    exports CMS;
}
