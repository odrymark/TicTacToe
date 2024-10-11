package dk.easv.tictactoefixed;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class StartController
{
    @FXML
    private Label label;
    private final Media introSound = new Media(Objects.requireNonNull(getClass().getResource("/dk/easv/tictactoefixed/sounds/gameIntro.mp3")).toString());
    private final MediaPlayer introPlayer = new MediaPlayer(introSound);
    @FXML
    private ImageView image;
    @FXML
    private Button oneBtn;
    @FXML
    private Button twoBtn;

    @FXML
    private void onePlayer() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TTTWindow.fxml"));
        Scene thisScene = label.getScene();
        Stage stage = (Stage) label.getScene().getWindow();
        double width = stage.getWidth() + (thisScene.getWidth() - stage.getWidth());
        double height = stage.getHeight() + (thisScene.getHeight() - stage.getHeight());
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setScene(scene);
        TTTController ttt = fxmlLoader.getController();
        ttt.getGameMode(false);
        ttt.firstTurn();
        introPlayer.stop();
        stage.show();
    }

    @FXML
    private void twoPlayer() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TTTWindow.fxml"));
        Scene thisScene = label.getScene();
        Stage stage = (Stage) label.getScene().getWindow();
        double width = stage.getWidth() + (thisScene.getWidth() - stage.getWidth());
        double height = stage.getHeight() + (thisScene.getHeight() - stage.getHeight());
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setScene(scene);
        TTTController ttt = fxmlLoader.getController();
        ttt.getGameMode(true);
        ttt.firstTurn();
        introPlayer.stop();
        stage.show();
    }

    public void introSoundEffect()
    {
        introPlayer.setVolume(0.35);
        introPlayer.play();
    }

    public void windowScaling()
    {
        GridPane.setHalignment(oneBtn, HPos.CENTER);
        GridPane.setHalignment(twoBtn, HPos.CENTER);
        GridPane.setHalignment(label, HPos.CENTER);

        Scene scene = label.getScene();
        image.fitWidthProperty().bind(scene.widthProperty());
        image.fitHeightProperty().bind(scene.heightProperty());

    }
}
