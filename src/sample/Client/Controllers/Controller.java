package sample.Client.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Client.Client;
import sample.Message;

public class Controller {

    InputStream inputStream;
    private String serverAddress;
    private int serverPort;
    private boolean isConnected = false;
    Message message;

    @FXML
    private AnchorPane root;
    
    @FXML
    private ListView MessageList;

    @FXML
    private TextField SendTextArea;

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
    
    @FXML
    public void initialize() throws IOException{
        Properties properties = new Properties();
        String propFilename = "sample/resources/config.properties";
        inputStream = this.getClass().getClassLoader().getResourceAsStream(propFilename);
        if (inputStream != null) {
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                MessageList.getItems().add("Файл конфигурации пустой или поврежден!");
            }
            this.serverAddress = properties.getProperty("IP");
            System.out.println(properties.getProperty("IP"));
            this.serverPort = Integer.parseInt(properties.getProperty("port"));
            System.out.println(properties.getProperty("port"));
            this.CWSOptionButton.setSelected(Boolean.parseBoolean(properties.getProperty("AutoConnect")));
            autoFillServerIPPort();
        } else {
            try {
                File propFile = new File("config.properties");
                propFile.createNewFile();
                PrintWriter propWriter = new PrintWriter(propFile);
                propWriter.write("#Client Configuration");
            } catch (IOException e) {
                MessageList.getItems().add("Не удалось создать файл конфигурации");
            }
        }
        ConnectButton.setOnAction((ActionEvent event) -> {
           
                try {
                    connect(SocketInputArea.getText().split(":")[0],
                            Integer.parseInt(SocketInputArea.getText().split(":")[1]));
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            
           
        });

    }
    
    private void connect(String serverAddress, int serverPort) throws IOException, InterruptedException{
        Client.connect(serverAddress, serverPort);
        if (Client.isConnected()) {
            isConnected = true;
            ConnectButton.setDisable(true);
            showLogInSignUpWindow(root);
            new Thread(() -> {
                try {
                    while (true) {
                        try {
                            if(Client.isAuthorisated() || !Client.isConnected()){
                                message = (Message) Client.messageUpdater();
                            }
                        } catch (ClassCastException e) {
                            
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("ggh");
                    MessageList.getItems().add("Connection lost");
                    ConnectButton.setDisable(false);
                    isConnected = false;
                }
            }).start();
        }
    }

    public void sendMessage() throws IOException {
        if (isConnected) {
            if (!(SendTextArea.getText().equals(""))) {
                Client.sendMessage(
                        new Message(SendTextArea.getText()));  //Метод отправки сообщения
            }
            SendTextArea.clear();
        } else {
            MessageList.getItems().add("You are not online");
        }
    }
    
    public void sendSystemMessage(Object object) throws IOException{           
        Client.sendSystemMessage(object);        
    }

    public void autoFillServerIPPort() {
        this.SocketInputArea.setText(this.serverAddress + ":" + this.serverPort);  //Заполняем сервер:порт из properties
    }
    
    public void showLogInSignUpWindow(Node node) throws IOException{
        Parent modalWindow = FXMLLoader.load(getClass().getResource("/sample/Client/FXML/reglogin.fxml"));
        Stage window = new Stage();
        window.setTitle("Log In");
        window.setResizable(false);
        window.setScene(new Scene(modalWindow));
        window.initModality(Modality.WINDOW_MODAL);
        window.initOwner(node.getScene().getWindow());
        window.show();
    }
}
