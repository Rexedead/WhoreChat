/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import sample.Message;
import sample.User;

/**
 *
 * @author Hate
 */
public class MessageList {
     private ObservableList<HBox> messages = FXCollections.observableArrayList();
     
     public void add(User user, Message message){
        ImageView avatar = new ImageView(user.getAvatar());
        Label nickName = new Label(user.getNickName());
        Label msg = new Label(message.getMessage());
        messages.add(new HBox(avatar, nickName, msg));
    }
    
//    public void delete(int id){messages.remove(id);}
//    
//    public String getUserId(int id){
//        Label label = (Label)messages.get(id).getChildren().get(2);
//        return label.getText();
//    }
     
    public void addSystem(Message message){
        messages.add(new HBox(new Label(message.getMessage())));
    }
    
    public ObservableList getMessageList(){return messages;}
}
