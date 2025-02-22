package com.example.speedtypingtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LandingPageController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void handleEasyBtn(ActionEvent event) throws IOException {
        switchToGamePage(event, "Easy");
    }

    @FXML
    void handleMediumBtn(ActionEvent event) throws IOException {
        switchToGamePage(event, "Medium");
    }

    @FXML
    void handleHardBtn(ActionEvent event) throws IOException {
        switchToGamePage(event, "Hard");
    }

    private void switchToGamePage(ActionEvent event, String level) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GamePage.fxml"));
        root = loader.load();

        GamePageController gameController = loader.getController();
        gameController.setLevel(level);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
