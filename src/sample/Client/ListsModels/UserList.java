/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Client.ListsModels;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import sample.User;

/**
 *
 * @author Hate
 */
public class UserList {
    private ObservableList<UserListModel> users = FXCollections.observableArrayList();
    UserListModel ULM;
    public void add(User user){
        ULM = new UserListModel(user.getId());
        ULM.setAvatar(user.getAvatar());
        ULM.setNickName(user.getNickName());
        users.add(ULM);
    }
    
    public void delete(int id){users.remove(id);}
    
    public void add(ArrayList<User> users){
        this.users = FXCollections.observableArrayList();
        for (User user : users) {
            add(user);
        }
    }
    
    public String getUserId(int id){
        return users.get(id).getUserId();
    }
    
    public ObservableList getUserList(){return users;}
}
