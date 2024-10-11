package dk.easv.tictactoefixed;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StartWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TicTacToe");
        stage.setScene(scene);
        StartController startController = fxmlLoader.getController();
        startController.introSoundEffect();
        startController.windowScaling();
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}