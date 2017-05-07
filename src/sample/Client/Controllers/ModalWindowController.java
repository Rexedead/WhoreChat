/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Client.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import sample.Client.Client;
import sample.ClientData;

import java.io.IOException;

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
                    Client.sendSystemMessage(new ClientData(LoginField.getText(), PasswordField.getText()));
                } catch (IOException ex) {
                    
                }
            }
        });
    }
  
}
