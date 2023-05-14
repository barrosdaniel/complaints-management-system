/*
Central Queensland University
COIT12200 - Software Design & Development (2023 Term 1)
Campus: External
Assignment 2 - Complaints Management System
Student ID: 12184305
Student Name: Daniel Barros
*/
package CMS;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for the Complaints Management System application. This class
 * contains the main method and is responsible for launching the application.
 */
public class CMSApp extends Application {


    private final String UI_FILE = "UserInterface.fxml";
    private final String UI_TITLE = "WyndhamNet | Complaints Management System";

    /**
     * Launches the application.Called from the main method.
     * @param stage
     * @throws java.io.IOException
     */
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

    /**
     * Main method for the Complaints Management System application.
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}