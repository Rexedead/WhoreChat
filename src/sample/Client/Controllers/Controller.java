package sample.Client.Controllers;

import com.sun.javafx.beans.event.AbstractNotifyListener;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Client.Client;
import sample.Client.ViewLists.FriendList;
import sample.Client.ViewLists.MessageList;
import sample.Client.ViewLists.UserList;
import sample.Message;
import sample.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.WorkerStateEvent;

public class Controller {

    InputStream inputStream;
    private String serverAddress;
    private int serverPort;
    private boolean isConnected = false;
    Client client = new Client();
    Message message;
    Thread messageUpdater;

    private UserList userList = new UserList();
    private MessageList msgList = new MessageList();
    private FriendList FrndList = new FriendList();

    private StringProperty connection = new SimpleStringProperty();

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
    public void initialize() throws IOException {
        messageUpdater = new Thread();
        connection.set("Connect");
        connection.addListener(new AbstractNotifyListener() {

            @Override
            public void invalidated(Observable observable) {
                ConnectButton.setText(connection.getValue());
            }
        });

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
                if (ConnectButton.getText().equals("Disconnect")) {
                    client.disconnect();
                } else {
                    connect(SocketInputArea.getText().split(":")[0],
                            Integer.parseInt(SocketInputArea.getText().split(":")[1]));
                }
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void connect(String serverAddress, int serverPort) throws IOException, InterruptedException {
        FXMLLoader.setLocation(getClass().getResource("/sample/Client/FXML/reglogin.fxml"));
        modalWindow = FXMLLoader.load();
        ModalWindowController = FXMLLoader.getController();
        client.connect(serverAddress, serverPort);
        if (client.isConnected()) {
            ModalWindowController.setClient(client);
            isConnected = true;
            ConnectButton.setDisable(true);
            showLogInSignUpWindow(root);
            if (client.isConnected()) {
                Task<Void> task = newTaskForMessage();
                connection.set("Disconnect");
                messageUpdater = new Thread(task);
                task.setOnSucceeded((WorkerStateEvent event) -> {
                    messageUpdater.interrupt();
                    connection.set("Connect");
                    client.disconnect();
                    userList.clear();
                });
                messageUpdater.setDaemon(true);
                messageUpdater.start();
            } else {
                ConnectButton.setDisable(false);
                connection.setValue("Connect");
            }
        }
        ConnectButton.setDisable(false);
        FXMLLoader = new FXMLLoader();
    }

    public void sendMessage() throws IOException {
        if (client.isConnected()) {
            if (!(SendTextArea.getText().equals(""))) {
                client.sendMessage(
                        new Message(SendTextArea.getText()));  //Метод отправки сообщения
            }
            SendTextArea.clear();
            
        } else {
            MessageList.getItems().add(new HBox(new Label("You are not online")));
        }
    }

    public void sendSystemMessage(Object object) throws IOException {
        client.sendSystemMessage(object);
    }

    public void autoFillServerIPPort() {
        this.SocketInputArea.setText(this.serverAddress + ":" + this.serverPort);  //Заполняем сервер:порт из properties
    }

    public void showLogInSignUpWindow(Node node) throws IOException {
        window = new Stage();
        window.setTitle("Log In");
        window.setResizable(false);
        window.setScene(new Scene(modalWindow));
        window.initModality(Modality.WINDOW_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        window.initOwner(node.getScene().getWindow());
        window.showAndWait();
    }
    private Task newTaskForMessage(){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                while (true) {
                    try{

                    Object q = client.messageUpdater();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            if (q instanceof ArrayList) {
                                userList.add((ArrayList<User>) q);
    //                                userList.delete(userList.getUserList().size() - 1);  //удаляем себя из общего массива, тк добавляемся через obj User
                            }
                            if (q instanceof Message) {
                                switch (((Message) q).getMessageType()) {

                                    case MESSAGE:
                                        message = (Message) q;
                                        try {
                                            msgList.add(userList.getUserById(message.getId()), message);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;

                                    case WHISPER:
                                        break;
                                    case FILEMESSAGE:
                                        break;
                                    case ADDFRIEND:
                                        break;
                                    case DELFRIEND:
                                        break;
                                    case USEROFFLINE:
                                        message = (Message) q;
                                        try {
                                            msgList.add(userList.getUserById(message.getId()), new Message(" has left the conversation"));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        userList.delete(userList.getIndexByMessageId(message.getId()));

                                        break;
                                }

                            } else if (q instanceof User) {
                                userList.add((User) q);
                                try {
                                    msgList.add((User) q, new Message(" is now Online"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }                  
                        }
                    });
                }catch(SocketException e){
                    break;
                }
            }
            return null;
        }

    };
    return task;
    }


}

