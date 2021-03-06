/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Client.ViewLists;

import sample.Client.ListsModels.ListModels.MessageListModel;
import sample.Client.ListsModels.ListModels.ImageMessageModel;
import sample.Client.ListsModels.ListModels.FileMessageModel;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sample.Message;
import sample.User;

/**
 *
 * @author Hate
 */
public class MessageList {
    private ObservableList<Parent> messages = FXCollections.observableArrayList();
    MessageListModel MSG;
    FileMessageModel FMSG;
    ImageMessageModel IMSG;

    public void add(User user, Message message) throws IOException{
        MSG = new MessageListModel();
        MSG.setAvatar(user.getAvatar());
        MSG.setNickName(user.getNickName());
        MSG.setMessage(message.getMessage());
        messages.add(MSG);
    }
    
    public void delete(int id){messages.remove(id);}
    
    public void addImageMessage(User user, Message message) throws IOException{
        IMSG = new ImageMessageModel();
        IMSG.setAvatar(user.getAvatar());
        IMSG.setNickName(user.getNickName());
        IMSG.setMessage(message.getMessage());
        IMSG.setImage(message.getImg());
        messages.add(IMSG);
    }
    
    public void addFileMessage(User user, Message message) throws IOException{
        FMSG = new FileMessageModel();
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
