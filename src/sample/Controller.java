package sample;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
=======
import java.io.*;
>>>>>>> Allan
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
<<<<<<< HEAD
        
=======
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

>>>>>>> Allan
    }
    
    private String messageUpdater() throws IOException{
        while(true){
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String answer = in.readLine();
            
            return null;
        }
    }
    
    public void connect(){
        ConnectButton.setDisable(true);
        String socket = SocketInputArea.getText();
        connect(socket.substring(0, socket.lastIndexOf(":") - 1), 
                Integer.parseInt(socket.substring(socket.lastIndexOf(":") + 1)));
    }
    public void connect(String serverAddress, int serverPort){
        ConnectButton.setDisable(true);
        try {
            InetAddress ipAddress = InetAddress.getByName(serverAddress);
            connection = new Socket(ipAddress, serverPort);
            new Thread(messageUpdater()).start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendMessage(){
        try {
            out = new PrintWriter(connection.getOutputStream());
            out.println(SendTextArea.getText());
            out.flush();
            out.close();
        } catch (IOException ex) {
            System.out.println("Error #3: ");
        }
    }
    
}
