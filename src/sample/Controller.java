package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Controller {

    InputStream inputStream;
    private boolean connectWithStart;
    private String serverAddress;
    private int serverPort;

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


    public void initialize() {

        Client client = new Client();
        client.connect();

    }

    private void setupProp() throws IOException {
        Properties properties = new Properties();
        String propFilename = "config.properties";
        inputStream = getClass().getClassLoader().getResourceAsStream(propFilename);

        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFilename + "' not found in the classpath");
        }

        // get the property value and print it out
        this.serverAddress = properties.getProperty("IP");
        this.serverPort = Integer.parseInt(properties.getProperty("port"));
        this.connectWithStart = Boolean.parseBoolean(properties.getProperty("AutoConnect"));
        autoFillServerIPPort();
        if (connectWithStart) {
            connect(this.serverAddress, this.serverPort);
        }
    }

    public void sendMessageViaKeyboard() {                                           //Метод отправки сообщения через Enter

        SendTextArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {                     //TODO: отправляется только после второго нажатия
                sendMessage(SendTextArea.getText());
            }
        });
    }

    public void sendMessage() {

        sendMessage(SendTextArea.getText());                             //Метод отправки сообщения кнопкой в GUI
    }

    public void autoFillServerIPPort() {
        this.SocketInputArea.setText(this.serverAddress + ":" + this.serverPort);  //Заполняем сервер:порт из conf.epic
    }
}
