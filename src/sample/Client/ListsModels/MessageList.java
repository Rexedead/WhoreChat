/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Client.ListsModels;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import sample.Client.Controllers.MessageListViewController;
import sample.Message;
import sample.User;

/**
 *
 * @author Hate
 */
public class MessageList {
     private ObservableList<Parent> messages = FXCollections.observableArrayList();
     FXMLLoader FXMLLoader = new FXMLLoader();
     Parent MessageArea;
     MessageListViewController MessageListViewController;
     
     public void add(User user, Message message) throws IOException{
        FXMLLoader.setLocation(getClass().getResource("/sample/Client/FXML/messageListView.fxml"));
        MessageArea = FXMLLoader.load();
        MessageListViewController = FXMLLoader.getController();
        MessageListViewController.setAvatar(user.getAvatar());
        MessageListViewController.setNickName(user.getNickName() + ": ");
        MessageListViewController.setMSG(message.getMessage());
        messages.add(MessageArea);
        FXMLLoader = new FXMLLoader();
        
    }
    
//    public void delete(int id){messages.remove(id);}
//    
//    public String getUserId(int id){
//        Label label = (Label)messages.get(id).getChildren().get(2);
//        return label.getText();
//    }
     
    public void addImageMessage(User user, Message message) throws IOException{
        FXMLLoader.setLocation(getClass().getResource("/sample/Client/FXML/messageListView.fxml"));
        MessageArea = FXMLLoader.load();
        MessageListViewController = FXMLLoader.getController();
        MessageListViewController.setAvatar(user.getAvatar());
        MessageListViewController.setNickName(user.getNickName() + ": ");
        MessageListViewController.setMSG(message.getMessage());
        MessageListViewController.setMsgData(message.getImg());
        messages.add(MessageArea);
        FXMLLoader = new FXMLLoader();
    }
    
    public void addFileMessage(User user, Message message) throws IOException{
        FXMLLoader.setLocation(getClass().getResource("/sample/Client/FXML/messageListView.fxml"));
        MessageArea = FXMLLoader.load();
        MessageListViewController  = FXMLLoader.getController();
        MessageListViewController = FXMLLoader.getController();
        MessageListViewController.setAvatar(user.getAvatar());
        MessageListViewController.setNickName(user.getNickName() + ": ");
        MessageListViewController.setMSG(message.getMessage());
        MessageListViewController.setFileMSG();
        messages.add(MessageArea);
        FXMLLoader = new FXMLLoader();
    }
     
    public void addSystem(Message message){
        messages.add(new HBox(new Label(message.getMessage())));
    }
    
    public ObservableList getMessageList(){return messages;}
}
