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
        args[0] = "-s";
            for(String arg:args){
                if(arg.equalsIgnoreCase("-s")){
                    System.out.println("Сервер");
                    // Здесь запускаем сервер в новом потоке!!!
                    return;
                }
            }
            launch(args);                       
        
        
    }
}
