/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Client.Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Client.Client;
import sample.ClientData;
import sample.Message;
import sample.MessageType;

/**
 *
 * @author Hate
 */
public class ModalWindowController{
    private Client client;
    
    @FXML
    private Button ExitButton;
    
    @FXML
    private Label statusLabel;
    
    @FXML
    private Label regStatusLabel;
    
    @FXML
    private Button LoginButton;
    
    @FXML
    private TextField LoginField;
    
    @FXML
    private TextField PasswordField;
    
    @FXML
    private Tab LogInTab;
    
    @FXML
    private Tab RegisterTab;
    
    @FXML
    private TextField RegNickNameField;
    
    @FXML
    private TextField RegEMailField;
    
    @FXML
    private TextField RegPasswordField;
    
    @FXML
    private Button RegButton;
    
    @FXML
    private Button RegExitButton;
    
    public void initialize(){
        
        LoginButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                if(!LoginField.getText().isEmpty() && !PasswordField.getText().isEmpty()){
                    client.sendSystemMessage(new ClientData(
                    LoginField.getText(), 
                    PasswordField.getText()));
                    isAuthorise();
                }else{
                    statusLabel.setText("Error: some field if empty");
                }
                } catch (IOException ex) {
                    
                }
            }
        });
        RegButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    if(!RegPasswordField.getText().isEmpty() && !RegEMailField.getText().isEmpty()
                            && !RegNickNameField.getText().isEmpty()){
                        client.sendSystemMessage(new ClientData(
                        RegPasswordField.getText(), 
                        RegEMailField.getText(), 
                        RegNickNameField.getText()));
                        isAuthorise();
                    }else{
                        regStatusLabel.setText("Error: some field if empty");
                    }
                } catch (IOException ex) {
                    
                }
            }
        });        
    }
    
    public void cancel(){
        try {
            client.disconnect();
        } catch (IOException ex) {
        
        }
        Stage stage = (Stage)ExitButton.getScene().getWindow();
        stage.close();
    }
    public void setClient(Client client){
        this.client = client;
    }
    private void isAuthorise(){
        try {
            Message message = (Message) client.messageUpdater();
            if(message.getMessageType() == MessageType.AUTHORISATION){
                Stage stage = (Stage)ExitButton.getScene().getWindow();
                client.disconnect();
                stage.close();
            }else{
                switch(message.getId()){
                    case("email exist"):
                        regStatusLabel.setText("Email address already in use");
                        break;
                    case("nickname exist"):
                        regStatusLabel.setText("Nickname already in use");
                        break;
                    case("invalid"):
                        statusLabel.setText("Wrong login or password");
                        break;
                    case("Try again later"):
                        statusLabel.setText("Try again later");
                        regStatusLabel.setText("Try again later");
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            
        }
    }
  
}
