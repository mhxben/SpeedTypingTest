package com.example.speedtypingtest;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GamePageController {

    @FXML
    private Label levelLabel, remainTimeLabel, remainingWord, wordLabel;
    @FXML
    private TextField wordField;
    @FXML
    private Button startButton, backButton;

    private final List<String> words = Arrays.asList(
            "Algorithm", "Variable", "Loop", "Function", "Class",
            "Object", "Array", "Recursion", "Compiler", "Inheritance",
            "Polymorphism", "Encapsulation", "Abstraction", "Framework", "Debugging"
    );

    private int wordsRemaining = 15;
    private int timePerWord;
    private Timeline wordTimer;
    private final Random random = new Random();

    public void setLevel(String level) {
        levelLabel.setText(level);

        switch (level.toLowerCase()) {
            case "easy":
                timePerWord = 20;
                break;
            case "medium":
                timePerWord = 15;
                break;
            case "hard":
                timePerWord = 10;
                break;
            default:
                timePerWord = 15;
        }

        remainTimeLabel.setText(timePerWord + "s");
        Platform.runLater(() -> startGame(new ActionEvent()));
    }

    @FXML
    void startGame(ActionEvent event) {
        if (wordTimer != null) {
            wordTimer.stop();
        }

        wordsRemaining = 15;
        remainingWord.setText("Words Left: " + wordsRemaining);
        wordLabel.setText(getRandomWord());
        wordField.clear();
        wordField.setDisable(false);
        wordField.requestFocus();
        startButton.setDisable(true);

        startWordTimer();
    }

    private void startWordTimer() {
        if (wordTimer != null) {
            wordTimer.stop();
        }

        wordTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            String timeText = remainTimeLabel.getText().replace("s", "").trim();

            if (!timeText.matches("\\d+")) {
                return;
            }

            int timeLeft = Integer.parseInt(timeText);
            timeLeft--;
            remainTimeLabel.setText(timeLeft + "s");

            if (timeLeft == 0) {
                endGame(false);
            }
        }));
        wordTimer.setCycleCount(timePerWord);
        wordTimer.play();
    }

    @FXML
    void handleWordView(ActionEvent event) {  // ✅ Fix: Ensure method exists in the controller
        String inputText = wordField.getText().trim();
        String targetWord = wordLabel.getText().trim();

        if (inputText.equalsIgnoreCase(targetWord)) {
            nextWord();
        }
    }

    private void nextWord() {
        if (wordTimer != null) {
            wordTimer.stop();
        }

        wordsRemaining--;
        remainingWord.setText("Words Left: " + wordsRemaining);

        if (wordsRemaining == 0) {
            endGame(true);
            return;
        }

        wordLabel.setText(getRandomWord());
        wordField.clear();
        wordField.requestFocus();
        remainTimeLabel.setText(timePerWord + "s");
        startWordTimer();
    }

    private void endGame(boolean won) {
        if (wordTimer != null) {
            wordTimer.stop();
        }

        wordField.setDisable(true);
        startButton.setDisable(false);

        if (won) {
            remainTimeLabel.setText("✅ You Won!");
            remainTimeLabel.setTextFill(Color.GREEN);
        } else {
            remainTimeLabel.setText("❌ You Lose!");
            remainTimeLabel.setTextFill(Color.RED);
        }
    }

    private String getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }

    @FXML
    void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
