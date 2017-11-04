package main;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    @FXML
    AnchorPane anchorPane;

    @FXML
    private void createNewGame(){
        try {
            BufferedReader input_file = new BufferedReader(new FileReader(new File("1_pacman_labyrinth.txt")));
            String line = "";
            int row = 0;
            int column = 0;
            ArrayList<String> arrLines = new ArrayList<>();
            while((line = input_file.readLine()) != null){
                row++;
                arrLines.add(line);
            }
            column = arrLines.get(0).length();

            GridPane gridPane = new GridPane();
            //Fülle Feld
            for(int i = 0; i < arrLines.size(); i++){
                String actLine = arrLines.get(i);
                char[] arrChars = actLine.toCharArray();
                for(int j = 0; j < arrChars.length; j++){
                    switch(arrChars[j]){
                        case "#":
                            Image image = new Image("img/wall.png");
                            gridPane.add(new ImageView(image), j, i);
                            break;
                    }
                }
            }
            anchorPane.getChildren().add(gridPane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
