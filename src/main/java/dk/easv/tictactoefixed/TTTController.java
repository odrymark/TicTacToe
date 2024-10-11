package dk.easv.tictactoefixed;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class TTTController
{
    @FXML
    private GridPane grid;
    @FXML
    private Label label;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button btn9;
    @FXML
    private Button[] buttons = new Button[9];
    private final Random rand = new Random();
    private boolean player1_turn = true;
    private boolean isTwoPlayer;
    private boolean isFirstTurn = true;
    private final String style = "-fx-font-family: 'MV Boli'; -fx-font-size: 40px;";
    private final Region background = new Region();
    private final Button restart = new Button("Restart");
    private final Button backToStart = new Button("MainMenu");

    public void getGameMode(boolean isTwoPlayer)
    {
        this.isTwoPlayer = isTwoPlayer;
    }

    @FXML
    private void buttonClicked(ActionEvent e)
    {
        for(int i = 0; i < 9; ++i)
        {
            if (e.getSource() == buttons[i])
            {
                //Single player mode
                if (player1_turn && !isTwoPlayer)
                {
                    if (buttons[i].getText().isEmpty())
                    {
                        buttons[i].setStyle("-fx-text-fill: red; "+style);
                        buttons[i].setText("X");
                        xSoundEffect();
                        label.setText("O turn");
                        if(checkForWin() || testIfFull())
                        {
                            return;
                        }

                        enemyTurn();
                        label.setText("X turn");
                        if(checkForWin())
                        {
                            return;
                        }
                        testIfFull();
                    }
                }
                //Two player mode
                else if(player1_turn)
                {
                    if (buttons[i].getText().isEmpty())
                    {
                        buttons[i].setStyle("-fx-text-fill: red; "+style);
                        buttons[i].setText("X");
                        xSoundEffect();
                        label.setText("O turn");
                        if(checkForWin())
                        {
                            return;
                        }
                        testIfFull();
                        player1_turn = false;
                    }
                }
                else if (buttons[i].getText().isEmpty() && isTwoPlayer)
                {
                    buttons[i].setStyle("-fx-text-fill: blue; "+style);
                    buttons[i].setText("O");
                    oSoundEffect();
                    player1_turn = true;
                    label.setText("X turn");
                    if(checkForWin())
                    {
                        return;
                    }
                    testIfFull();
                }
            }
        }

    }

    public void firstTurn()
    {
        Button[] btns = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
        System.arraycopy(btns, 0, buttons, 0, 9);
        background.setStyle("-fx-background-color: black;");
        background.setMinHeight(10);
        background.setPrefHeight(10);
        grid.add(background, 0, 0, 3, 1);
        label.toFront();
        GridPane.setHalignment(label, HPos.CENTER);

        if(isTwoPlayer)
        {
            if (rand.nextInt(2) == 0)
            {
                player1_turn = true;
                label.setText("X turn");
            }
            else
            {
                player1_turn = false;
                label.setText("O turn");
            }
        }
        else
        {
            if (rand.nextInt(2) == 0)
            {
                player1_turn = true;
                label.setText("X turn");
            }
            else
            {
                enemyTurn();
                label.setText("X turn");
            }
        }
    }

    private boolean checkForWin()
    {
        //Check for columns
        for(int i = 0; i < 3; i++)
        {
            if(Objects.equals(buttons[i].getText(), buttons[i+3].getText()) && Objects.equals(buttons[i+3].getText(), buttons[i+6].getText()) && !buttons[i].getText().isEmpty())
            {
                winner(i, i+3, i+6);

                if(buttons[i].getText().equals("O"))
                {
                    label.setText("O wins");
                }
                else
                {
                    label.setText("X wins");
                }
                return true;
            }
        }

        //Check for rows
        for(int i = 0; i < 7; i+=3)
        {
            if(Objects.equals(buttons[i].getText(), buttons[i+1].getText()) && Objects.equals(buttons[i+1].getText(), buttons[i+2].getText()) && !buttons[i].getText().isEmpty())
            {
                winner(i, i+1, i+2);
                if(buttons[i].getText().equals("O"))
                {
                    label.setText("O wins");
                }
                else
                {
                    label.setText("X wins");
                }
                return true;
            }
        }

        //Check for diagonals
        if(Objects.equals(buttons[0].getText(), buttons[4].getText()) && Objects.equals(buttons[4].getText(), buttons[8].getText()) && !buttons[0].getText().isEmpty())
        {
            winner(0, 4, 8);
            if(buttons[0].getText().equals("O"))
            {
                label.setText("O wins");
            }
            else
            {
                label.setText("X wins");
            }
            return true;
        }
        else if(Objects.equals(buttons[6].getText(), buttons[4].getText()) && Objects.equals(buttons[4].getText(), buttons[2].getText()) && !buttons[6].getText().isEmpty())
        {
            winner(6, 4, 2);

            if(buttons[6].getText().equals("O"))
            {
                label.setText("O wins");
            }
            else
            {
                label.setText("X wins");
            }
            return true;
        }

        return false;
    }

    private boolean testIfFull()
    {
        for(int i = 0; i < 9; i++)
        {
            if(buttons[i].getText().isEmpty())
            {
                return false;
            }
        }

        label.setText("Draw");
        for(int i = 0; i < 9; i++)
        {
            buttons[i].setDisable(true);
        }
        showBtns();
        return true;
    }

    private void winner(int a, int b, int c)
    {
        buttons[a].setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        buttons[b].setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        buttons[c].setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));

        for(int i = 0; i < 9; ++i)
        {
            buttons[i].setDisable(true);
        }
        showBtns();
    }

    private void showBtns()
    {
        //Add reset button
        restart.setPrefWidth(75);
        restart.setPrefHeight(50);
        restart.setOnAction(_ ->
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TTTWindow.fxml"));
            try
            {
                Scene thisScene = label.getScene();
                Stage stage = (Stage) label.getScene().getWindow();
                double width = stage.getWidth() + (thisScene.getWidth() - stage.getWidth());
                double height = stage.getHeight() + (thisScene.getHeight() - stage.getHeight());
                Scene scene = new Scene(fxmlLoader.load(), width, height);
                stage.setScene(scene);
                TTTController ttt = fxmlLoader.getController();
                ttt.getGameMode(isTwoPlayer);
                ttt.firstTurn();
                stage.show();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        });
        grid.add(restart, 2, 0);
        GridPane.setHalignment(restart, HPos.CENTER);

        //Add backToStart button
        backToStart.setPrefWidth(75);
        backToStart.setPrefHeight(50);
        backToStart.setOnAction(_ ->
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StartWindow.fxml"));
            try
            {
                Scene thisScene = label.getScene();
                Stage stage = (Stage) label.getScene().getWindow();
                double width = stage.getWidth() + (thisScene.getWidth() - stage.getWidth());
                double height = stage.getHeight() + (thisScene.getHeight() - stage.getHeight());
                Scene scene = new Scene(fxmlLoader.load(), width, height);
                StartController sc = fxmlLoader.getController();
                sc.windowScaling();
                stage.setScene(scene);
                stage.show();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        });

        grid.add(backToStart, 0, 0);
        GridPane.setHalignment(backToStart, HPos.CENTER);
    }

    private void enemyTurn()
    {
        //Place for the first time
        if(isFirstTurn)
        {
            if(Objects.equals(buttons[4].getText(), ""))
            {
                while(true)
                {
                    int randBtn = rand.nextInt(9);
                    if(buttons[randBtn].getText().isEmpty())
                    {
                        buttons[randBtn].setText("O");
                        buttons[randBtn].setStyle("-fx-text-fill: blue; "+style);
                        break;
                    }
                }
            }
            else
            {
                int corner = rand.nextInt(4);
                switch(corner)
                {
                    case 0:
                        buttons[0].setText("O");
                        buttons[0].setStyle("-fx-text-fill: blue; "+style);
                        break;
                    case 1:
                        buttons[2].setText("O");
                        buttons[2].setStyle("-fx-text-fill: blue; "+style);
                        break;
                    case 2:
                        buttons[6].setText("O");
                        buttons[6].setStyle("-fx-text-fill: blue; "+style);
                        break;
                    case 3:
                        buttons[8].setText("O");
                        buttons[8].setStyle("-fx-text-fill: blue; "+style);
                        break;
                }
            }
            isFirstTurn = false;
            return;
        }

        //Testing for diagonals
        for(int i = 0; i < 2; i++)
        {
            String testFor;

            if(i == 0)
            {
                testFor = "O";
            }
            else
            {
                testFor = "X";
            }

            if(Objects.equals(buttons[0].getText(), buttons[4].getText()) && buttons[0].getText().equals(testFor) && buttons[8].getText().isEmpty())
            {
                buttons[8].setText("O");
                buttons[8].setStyle("-fx-text-fill: blue; "+style);
                return;
            }
            else if(Objects.equals(buttons[0].getText(), buttons[8].getText()) && buttons[0].getText().equals(testFor) && buttons[4].getText().isEmpty())
            {
                buttons[4].setText("O");
                buttons[4].setStyle("-fx-text-fill: blue; "+style);
                return;
            }
            else if(Objects.equals(buttons[4].getText(), buttons[8].getText()) && buttons[4].getText().equals(testFor) && buttons[0].getText().isEmpty())
            {
                buttons[0].setText("O");
                buttons[0].setStyle("-fx-text-fill: blue; "+style);
                return;
            }
            else if(Objects.equals(buttons[6].getText(), buttons[4].getText()) && buttons[6].getText().equals(testFor) && buttons[2].getText().isEmpty())
            {
                buttons[2].setText("O");
                buttons[2].setStyle("-fx-text-fill: blue; "+style);
                return;
            }
            else if(Objects.equals(buttons[6].getText(), buttons[2].getText()) && buttons[6].getText().equals(testFor) && buttons[4].getText().isEmpty())
            {
                buttons[4].setText("O");
                buttons[4].setStyle("-fx-text-fill: blue; "+style);
                return;
            }
            else if(Objects.equals(buttons[4].getText(), buttons[2].getText()) && buttons[4].getText().equals(testFor) && buttons[6].getText().isEmpty())
            {
                buttons[6].setText("O");
                buttons[6].setStyle("-fx-text-fill: blue; "+style);
                return;
            }
        }

        //Testing for columns
        for(int i = 0; i < 2; i++)
        {
            String testFor;
            if(i == 0)
            {
                testFor = "O";
            }
            else
            {
                testFor = "X";
            }

            for(int j = 0; j < 3; j++)
            {
                if (Objects.equals(buttons[j].getText(), buttons[j + 3].getText()) && buttons[j].getText().equals(testFor) && buttons[j + 6].getText().isEmpty())
                {
                    buttons[j + 6].setText("O");
                    buttons[j + 6].setStyle("-fx-text-fill: blue; "+style);
                    return;
                }
                else if (Objects.equals(buttons[j].getText(), buttons[j + 6].getText()) && buttons[j].getText().equals(testFor) && buttons[j + 3].getText().isEmpty())
                {
                    buttons[j + 3].setText("O");
                    buttons[j + 3].setStyle("-fx-text-fill: blue; "+style);
                    return;
                }
                else if (Objects.equals(buttons[j + 3].getText(), buttons[j + 6].getText()) && buttons[j + 3].getText().equals(testFor) && buttons[j].getText().isEmpty())
                {
                    buttons[j].setText("O");
                    buttons[j].setStyle("-fx-text-fill: blue; "+style);
                    return;
                }
            }
        }

        //Testing for rows
        for(int i = 0; i < 2; i++)
        {
            String testFor;
            if(i == 0)
            {
                testFor = "O";
            }
            else
            {
                testFor = "X";
            }

            for(int j = 0; j < 7; j+=3)
            {
                if(Objects.equals(buttons[j].getText(), buttons[j+1].getText()) && buttons[j].getText().equals(testFor) && buttons[j+2].getText().isEmpty())
                {
                    buttons[j + 2].setText("O");
                    buttons[j + 2].setStyle("-fx-text-fill: blue; "+style);
                    return;
                }
                else if(Objects.equals(buttons[j].getText(), buttons[j+2].getText()) && buttons[j].getText().equals(testFor) && buttons[j+1].getText().isEmpty())
                {
                    buttons[j + 1].setText("O");
                    buttons[j + 1].setStyle("-fx-text-fill: blue; "+style);
                    return;
                }
                else if(Objects.equals(buttons[j+1].getText(), buttons[j+2].getText()) && buttons[j+1].getText().equals(testFor) && buttons[j].getText().isEmpty())
                {
                    buttons[j].setText("O");
                    buttons[j].setStyle("-fx-text-fill: blue; "+style);
                    return;
                }
            }
        }

        //IMPLEMENT LOGIC OR LEAVE TO NOT ALWAYS DRAW
        while(true)
        {
            int randBtn = rand.nextInt(9);
            if(buttons[randBtn].getText().isEmpty())
            {
                buttons[randBtn].setText("O");
                buttons[randBtn].setStyle("-fx-text-fill: blue; "+style);
                break;
            }
        }
    }

    private void xSoundEffect() {
        Media xSound = new Media(Objects.requireNonNull(getClass().getResource("/dk/easv/tictactoefixed/sounds/xPlayerSound.mp3")).toString());
        MediaPlayer xPlayer = new MediaPlayer(xSound);
        xPlayer.setVolume(0.5);
        xPlayer.play();
    }

    private void oSoundEffect() {
        Media oSound = new Media(Objects.requireNonNull(getClass().getResource("/dk/easv/tictactoefixed/sounds/oPlayerSound.mp3")).toString());
        MediaPlayer oPlayer = new MediaPlayer(oSound);
        oPlayer.setVolume(0.5);
        oPlayer.play();
    }
}
