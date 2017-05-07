package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;


public class Controller {

    InputStream inputStream;
    private String serverAddress;
    private int serverPort;
    Client client = new Client();
    private boolean isConnected = false;
    Message message;
    User User;


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


    public void initialize() throws IOException {

        Properties properties = new Properties();
        String propFilename = "config.properties";

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

        if (CWSOptionButton.isSelected()) {
            connect(this.serverAddress, this.serverPort);
        }


        ConnectButton.setOnAction((ActionEvent event) -> {
            connect(SocketInputArea.getText().split(":")[0],
                    Integer.parseInt(SocketInputArea.getText().split(":")[1]));
        });

    }

    private void connect(String serverAddress, int serverPort) {
        client = new Client(serverAddress, serverPort);

        if (client.isConnected()) {
            isConnected = true;
            ConnectButton.setDisable(true);
//            DBworker db = new DBworker();
//            ObservableList<String> items =FXCollections.observableArrayList (db.readFromSQLwhenLogining("Rexedead","111"));
//            OnlineList.setItems(items);

            new Thread(() -> {
                try {
                    while (true) {
                        try {
                            message = (Message) client.messageUpdater();
                        } catch (ClassCastException e) {
                            User = (User) client.messageUpdater();
                        }
                    }
                } catch (IOException e) {
                    MessageList.getItems().add("Connection lost");
                    ConnectButton.setDisable(false);
                    isConnected = false;
                } catch (ClassNotFoundException ex) {
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
                client.sendMessage(
                        new Message(SendTextArea.getText()));  //Метод отправки сообщения
            }
            SendTextArea.clear();
        } else {
            MessageList.getItems().add("You are not online");
        }
    }

    public void autoFillServerIPPort() {
        this.SocketInputArea.setText(this.serverAddress + ":" + this.serverPort);  //Заполняем сервер:порт из properties
    }
}
