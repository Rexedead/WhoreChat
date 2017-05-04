package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class Controller {

    InputStream inputStream;
    private boolean connectWithStart;
    private String serverAddress;
    private int serverPort;
    Client client;

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

//        Properties properties = new Properties();
//        String propFilename = "config.properties";
//        inputStream = getClass().getClassLoader().getResourceAsStream(propFilename);
//
//        if (inputStream != null) {
//            properties.load(inputStream);
//        } else {
//            throw new FileNotFoundException("property file '" + propFilename + "' not found in the classpath");
//        }
//        this.serverAddress = properties.getProperty("IP");
//        this.serverPort = Integer.parseInt(properties.getProperty("port"));
//        this.connectWithStart = Boolean.parseBoolean(properties.getProperty("AutoConnect"));
//        autoFillServerIPPort();

        if (connectWithStart) {
            connect(this.serverAddress, this.serverPort);
        }
    }
    
    private void connect(String serverAddress, int serverPort){
        client = new Client(serverAddress, serverPort);
        if(client.isConnected()){
            //Меняется кнопка
        }
    }

//    public void sendMessage() {
//        client.sendMessage(SendTextArea.getText());                             //Метод отправки сообщения кнопкой в GUI
//    }
//
//    public void autoFillServerIPPort() {
//        this.SocketInputArea.setText(this.serverAddress + ":" + this.serverPort);  //Заполняем сервер:порт из conf.epic
//    }
}
