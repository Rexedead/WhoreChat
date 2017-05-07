/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Client.Controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    
    @FXML
    private Button ExitButton;
    
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
                    Client.sendSystemMessage(new ClientData(
                            LoginField.getText(), 
                            PasswordField.getText()));
                    isAuthorise();
                } catch (IOException ex) {
                    
                }
            }
        });
        RegButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    Client.sendSystemMessage(new ClientData(
                            RegPasswordField.getText(), 
                            RegEMailField.getText(), 
                            RegNickNameField.getText()));
                    isAuthorise();
                } catch (IOException ex) {
                    
                }
            }
        });        
    }
    
    public void cancel(){
        try {
            Client.disconnect();
        } catch (IOException ex) {
        
        }
        Stage stage = (Stage)ExitButton.getScene().getWindow();
        stage.close();
    }
    
    private void isAuthorise(){
        try {
            Message message = (Message) Client.messageUpdater();
            if(message.getMessageType() == MessageType.AUTHORISATION){
                Client.setAuthorisated();
                Stage stage = (Stage)ExitButton.getScene().getWindow();
                stage.close();
            }
        } catch (IOException | ClassNotFoundException ex) {
            
        }
    }
  
}
