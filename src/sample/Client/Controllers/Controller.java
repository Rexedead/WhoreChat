package sample.Client.Controllers;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Client.Client;
import sample.Client.ListsModels.FriendList;
import sample.Client.ListsModels.MessageList;
import sample.Client.ListsModels.UserList;
import sample.Message;
import sample.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    InputStream inputStream;
    private String serverAddress;
    private int serverPort;
    private boolean isConnected = false;
    Client client;
    Message message;
    
    private UserList userList = new UserList();
    private MessageList msgList = new MessageList();
    private FriendList FrndList = new FriendList();

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
    
    private Parent modalWindow;
    private Stage window;
    private FXMLLoader FXMLLoader = new FXMLLoader();
    private ModalWindowController ModalWindowController;
    
    @FXML
    public void initialize() throws IOException{
        OnlineList.setItems(userList.getUserList());
        MessageList.setItems(msgList.getMessageList());
        FriendList.setItems(FrndList.getUserList());
        
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
        FXMLLoader.setLocation(getClass().getResource("/sample/Client/FXML/reglogin.fxml"));
        modalWindow = FXMLLoader.load();
        ModalWindowController = FXMLLoader.getController();
        Client client = new Client(serverAddress, serverPort);
        msgList.add(new User(new Image("file:D:/projects/WhoreChat/src/sample/resources/12.jpg"),
        "12", "Hate"), new Message("У меня получилось!!!"));
        if (client.isConnected()) {
            ModalWindowController.setClient(client);
            isConnected = true;
            ConnectButton.setDisable(true);
            showLogInSignUpWindow(root);
            if (client.isConnected()){
                new MessageUpdater().start();
            }else{
                isConnected = false;
                ConnectButton.setDisable(false);
            }
        }
        FXMLLoader = new FXMLLoader();
    }

    public void sendMessage() throws IOException {
        if (client != null) {
            if (!(SendTextArea.getText().equals(""))) {
                client.sendMessage(
                        new Message(SendTextArea.getText()));  //Метод отправки сообщения
            }
            SendTextArea.clear();
        } else {
            MessageList.getItems().add(new HBox(new Label("You are not online")));
        }
    }
    
    public void sendSystemMessage(Object object) throws IOException{           
        client.sendSystemMessage(object);        
    }

    public void autoFillServerIPPort() {
        this.SocketInputArea.setText(this.serverAddress + ":" + this.serverPort);  //Заполняем сервер:порт из properties
    }
    
    public void showLogInSignUpWindow(Node node) throws IOException{
        window = new Stage();
        window.setTitle("Log In");
        window.setResizable(false);
        window.setScene(new Scene(modalWindow));
        window.initModality(Modality.WINDOW_MODAL);
        window.setOnCloseRequest((WindowEvent event) -> {
            ModalWindowController.cancel();
        });
        window.initOwner(node.getScene().getWindow());
        window.showAndWait();
    }
    
    class MessageUpdater extends Thread{

        @Override
        public void run() {

            try {
                while (true) {
                    try {
                        message = (Message) client.messageUpdater();
                    } catch (ClassCastException e) {

                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                MessageList.getItems().add("Connection lost");
                ConnectButton.setDisable(false);
                isConnected = false;
                interrupt();
            }
        }
    } 
}

