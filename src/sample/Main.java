package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        try{
            for (String arg : args) {
                arg.equalsIgnoreCase("-s"); // Проверка аргумента запуска. Проверка на ошибку сделана для предотвращения краша программы при пустом массиве
            }                               // args(отсутствие аргументов запуска).
        }catch(NullPointerException e){
            launch(args);                   //В случае отсутствия аргументов запуска запускать GUI
        }
        
    }
}
