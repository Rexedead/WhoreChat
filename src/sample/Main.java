package sample;

import sample.Server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/sample.fxml"));
        primaryStage.setTitle("Whore Chat");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(620);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        for(String arg:args){
            if(arg.equalsIgnoreCase("-s")){     //  Проверка параметра запуска. Если среди аргументов запуска есть -s, то запускать сервер
                new Server();                   // Здесь запускаем сервер
                return;                         // В случае запуска сервера запретить дальнейшее выполнение метода main(Запуск клиентской части).
            }
        }
        launch(args);

    }
}
