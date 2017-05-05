package sample;

import java.awt.event.KeyEvent;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javax.xml.ws.handler.Handler;


public class Controller {

    InputStream inputStream;
    private boolean connectWithStart;
    private String serverAddress;
    private int serverPort;
    Client client = new Client();

    @FXML
    private TextArea MessageList;

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


    public void initialize() throws FileNotFoundException, IOException {

        Properties properties = new Properties();
        String propFilename = "config.properties";

        inputStream = getClass().getClassLoader().getResourceAsStream(propFilename);

        if (inputStream != null) {
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                MessageList.appendText("Файл конфигурации пустой или поврежден" + "\n");
            }
        } else {
            try {
                File propFile = new File("config.properties");
                propFile.createNewFile();
                PrintWriter propWriter = new PrintWriter(propFile);
                propWriter.write("#Client Configuration");
            } catch (IOException e) {
                MessageList.appendText("Не удалось создать файл конфигурации" + "\n");
            }
        }
        this.serverAddress = properties.getProperty("IP");
        System.out.println(properties.getProperty("IP"));
        this.serverPort = Integer.parseInt(properties.getProperty("port"));
        System.out.println(properties.getProperty("port"));
        this.connectWithStart = Boolean.parseBoolean(properties.getProperty("AutoConnect"));
        autoFillServerIPPort();

        if (connectWithStart) {
            connect(this.serverAddress, this.serverPort);
        }

        
        ConnectButton.setOnAction((ActionEvent event) -> {
            connect(SocketInputArea.getText().split(":")[0], 
                    Integer.parseInt(SocketInputArea.getText().split(":")[1]));
        });
        
    }
    
    private void connect(String serverAddress, int serverPort){
        client = new Client(serverAddress, serverPort);
        if(client.isConnected()){
            ConnectButton.setDisable(true);
            new Thread(() -> {
                try {
                    while(client.isConnected()){
                        MessageList.appendText(client.messageUpdater() + "\n");
                    }
                    ConnectButton.setDisable(false);
                } catch (IOException e) {
                    
                }
            }).start();
        }
    }

    public void sendMessage() {
        if(client.isConnected()){
            client.sendMessage(SendTextArea.getText());                             //Метод отправки сообщения кнопкой в GUI
            SendTextArea.clear();
        }else{
            MessageList.appendText("You are not online\n");
        }
    }

    public void autoFillServerIPPort() {
        this.SocketInputArea.setText(this.serverAddress + ":" + this.serverPort);  //Заполняем сервер:порт из propeties
    }
}
