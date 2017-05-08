/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Client;

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
    private ObservableList<HBox> users = FXCollections.observableArrayList();
    
    public void add(User user){
        ImageView avatar = new ImageView(user.getAvatar());
        Label nickName = new Label(user.getNickName());
        Label id = new Label(user.getId());
        id.setVisible(false);
        users.add(new HBox(avatar, nickName, id));
    }
    
    public void delete(int id){users.remove(id);}
    
    public void add(ArrayList<User> users){
        this.users = FXCollections.observableArrayList();
        for (User user : users) {
            add(user);
        }
    }
    
    public String getUserId(int id){
        Label label = (Label)users.get(id).getChildren().get(2);
        return label.getText();
    }
    
    public ObservableList getUserList(){return users;}
}
