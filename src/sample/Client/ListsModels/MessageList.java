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
import javafx.scene.layout.HBox;
import sample.Client.Controllers.MessageListViewController;
import sample.Message;
import sample.User;

/**
 *
 * @author Hate
 */
public class MessageList {
    private ObservableList<Parent> messages = FXCollections.observableArrayList();
    MessageListModel MSG = new MessageListModel();
    FileMessageModel FMSG = new FileMessageModel();
    ImageMessageModel IMSG = new ImageMessageModel();

    public void add(User user, Message message) throws IOException{
        MSG.setAvatar(user.getAvatar());
        MSG.setNickName(user.getNickName());
        MSG.setMessage(message.getMessage());
        messages.add(MSG);
    }
    
    public void delete(int id){messages.remove(id);}
    
    public void addImageMessage(User user, Message message) throws IOException{
        IMSG.setAvatar(user.getAvatar());
        IMSG.setNickName(user.getNickName());
        IMSG.setMessage(message.getMessage());
        IMSG.setImage(message.getImg());
        messages.add(IMSG);
    }
    
    public void addFileMessage(User user, Message message) throws IOException{
        FMSG.setAvatar(user.getAvatar());
        FMSG.setNickName(user.getNickName());
        FMSG.setMessage(message.getMessage());
        FMSG.setDATA(message.getFile());
        messages.add(FMSG);
    }
     
    public void addSystem(Message message){
        messages.add(new HBox(new Label(message.getMessage())));
    }
    
    public ObservableList getMessageList(){return messages;}
}
