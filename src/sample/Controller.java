package sample;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class Controller {
//    Client client = null;
    private Socket connection;
    private PrintWriter out;
    private BufferedReader in;
    private boolean connectWithStart = false;
    private String serverAddress = "127.0.0.1";
    private int serverPort = 2283;
    
    @FXML
    private ListView MessageList;
    
    @FXML
    private TextArea SendTextArea;
    
    @FXML
    private Button SendButton;
    
    @FXML
    private Accordion Menu;
    
    @FXML
    private ListView OnlineList;
    
    @FXML
    private ListView FriendList;
    
    @FXML
    private TextField SocketInputArea;
    
    @FXML
    private Button ConnectButton;
    
    @FXML
    private CheckBox CWSOptionButton;
    
    public void initialize(URL location, ResourceBundle resources) throws IOException{
        File file=new File("Conf.epic");
       if (file.exists()){
           BufferedReader epic=new BufferedReader(new FileReader(file));
           String s;
           while ((s = epic.readLine()) != null){
               switch (s){
                   
               }


           }
       }
       else {
           file.createNewFile();
       }

    }
    
    private String messageUpdater() throws IOException{
        while(true){
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String answer = in.readLine();
            System.out.println(answer);
            return null;
        }
    }
    
    
}
