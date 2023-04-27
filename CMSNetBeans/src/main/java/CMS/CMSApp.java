package CMS;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CMSApp extends Application {

    private String UI_FILE = "UserInterface.fxml";
    private String UI_TITLE = "WyndhamNet | Complaints Management System";
    
    @Override
    public void start (Stage stage) throws IOException {
        // Load UI FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(UI_FILE));
        Parent root = fxmlLoader.load();
        
        // Set-up JavaFX scene
        Scene scene = new Scene(root, 1200, 900);
        stage.setScene(scene);
        stage.setTitle(UI_TITLE);
        
        // Show UI
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}