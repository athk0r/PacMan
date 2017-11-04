package main;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    @FXML
    AnchorPane anchorPane;
    @FXML
    VBox vBox;
    GridPane gridPane;
    GridPane gridPunkte = new GridPane();
    Image image_pac = new Image("img/pacman.png");
    Image image_point_eaten = new Image("img/point_eaten.png");
    Label punkte = new Label("0");

    char[][] board;
    int pac_column;
    int pac_row;


    @FXML
    private void createNewGame(){
        try {
            BufferedReader input_file = new BufferedReader(new FileReader(new File("src/1_pacman_labyrinth.txt")));
            String line = "";
            int row = 0;
            int column = 0;
            ArrayList<String> arrLines = new ArrayList<>();
            while((line = input_file.readLine()) != null){
                row++;
                arrLines.add(line);
            }
            column = arrLines.get(0).length();
            gridPane = new GridPane();
            pac_column = -1;
            pac_row = -1;
            board = new char[row][column];
            //Fülle Feld
            for(int i = 0; i < arrLines.size(); i++){
                String actLine = arrLines.get(i);
                System.out.println(actLine);
                char[] arrChars = actLine.toCharArray();
                for(int c = 0; c < arrChars.length; c++){
                    board[i][c] = arrChars[c];
                }
                for(int j = 0; j < arrChars.length; j++){
                    switch(arrChars[j]){
                        case '#':
                            Image image_wall = new Image("img/wall.png");
                            gridPane.add(new ImageView(image_wall), j, i);
                            break;
                        case '-':
                            Image image_home = new Image("img/home.png");
                            gridPane.add(new ImageView(image_home), j, i);
                            break;
                        case ' ':
                            Image image_space = new Image("img/blank.png");
                            gridPane.add(new ImageView(image_space), j, i);
                            break;
                        case '.':
                            Image image_dot = new Image("img/point.png");
                            gridPane.add(new ImageView(image_dot), j, i);
                            if(pac_column == -1 && pac_row == -1){
                                pac_column = j;
                                pac_row = i;
                            }
                            break;
                        default:
                            Image image_blank = new Image("img/blank.png");
                            gridPane.add(new ImageView(image_blank), j, i);
                            break;
                    }
                }
            }
            gridPane.add(new ImageView(image_pac), pac_column, pac_row);
            gridPunkte.add(new Label("Punkte:"), 0, arrLines.size());
            gridPunkte.add(punkte, 1, arrLines.size());
            vBox.getChildren().add(gridPane);
            vBox.getChildren().add(gridPunkte);
            board[pac_row][pac_column] = 'e';
            play();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void play() {
        Scene scene = anchorPane.getScene();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch(keyEvent.getCode()){
                    case DOWN:
                        //Wandkontrolle
                        if(board[pac_row+1][pac_column] != '#'){
                            if(board[pac_row+1][pac_column] == '.'){
                                int tmp = Integer.parseInt(punkte.getText());
                                punkte.setText(Integer.toString(++tmp));
                            }
                            board[pac_row+1][pac_column] = 'e';
                            board[pac_row][pac_column] = 'x';
                            gridPane.add(new ImageView(image_pac), pac_column, pac_row+1);
                            gridPane.add(new ImageView(image_point_eaten), pac_column, pac_row);
                            pac_row += 1;
                        }
                        break;
                    case UP:
                        if(board[pac_row-1][pac_column] != '#'){
                            if(board[pac_row-1][pac_column] == '.'){
                                int tmp = Integer.parseInt(punkte.getText());
                                punkte.setText(Integer.toString(++tmp));
                            }
                            board[pac_row-1][pac_column] = 'e';
                            board[pac_row][pac_column] = 'x';
                            gridPane.add(new ImageView(image_pac), pac_column, pac_row-1);
                            gridPane.add(new ImageView(image_point_eaten), pac_column, pac_row);
                            pac_row -= 1;
                        }
                        break;
                    case RIGHT:
                        if(board[pac_row][pac_column+1] != '#'){
                            if(board[pac_row][pac_column+1] == '.'){
                                int tmp = Integer.parseInt(punkte.getText());
                                punkte.setText(Integer.toString(++tmp));
                            }
                            board[pac_row][pac_column+1] = 'e';
                            board[pac_row][pac_column] = 'x';
                            gridPane.add(new ImageView(image_pac), pac_column+1, pac_row);
                            gridPane.add(new ImageView(image_point_eaten), pac_column, pac_row);
                            pac_column += 1;
                        }
                        break;
                    case LEFT:
                        if(board[pac_row][pac_column-1] != '#'){
                            if(board[pac_row][pac_column-1] == '.'){
                                int tmp = Integer.parseInt(punkte.getText());
                                punkte.setText(Integer.toString(++tmp));
                            }
                            board[pac_row][pac_column-1] = 'e';
                            board[pac_row][pac_column] = 'x';
                            gridPane.add(new ImageView(image_pac), pac_column-1, pac_row);
                            gridPane.add(new ImageView(image_point_eaten), pac_column, pac_row);
                            pac_column -= 1;
                        }
                        break;
                }
            }
        });
    }
}
