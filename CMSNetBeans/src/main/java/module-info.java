module CMS {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens CMS to javafx.fxml;
    exports CMS;
}
