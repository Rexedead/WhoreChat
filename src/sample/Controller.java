package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;


public class Controller {

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


}
