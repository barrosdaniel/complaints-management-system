module CMS {
    requires javafx.controls;
    requires javafx.fxml;

    opens CMS to javafx.fxml;
    exports CMS;
}
