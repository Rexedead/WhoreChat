/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Client.ViewLists;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import sample.Client.ListsModels.ListModels.UserListModel;
import sample.User;

import java.util.ArrayList;
import javafx.scene.image.ImageView;

/**
 * @author Hate
 */
public class UserList {
    private ObservableList<UserListModel> users = FXCollections.observableArrayList();
    UserListModel ULM;
    private final Image DefaultImage = new Image("/sample/resources/empty_user.jpg");

    public void add(User user) {
        ULM = new UserListModel(user.getId(), user.getAvatar(), user.getNickName());
        if (user.getAvatarImage() == null) {
            ULM.setAvatarImage(DefaultImage);
        }
        users.add(ULM);
    }

    public void delete(int indexInArray) {

        users.remove(indexInArray);

    }

    public void add(ArrayList<User> users) {
//        this.users = FXCollections.observableArrayList();
        for (User user : users) {
            add(user);
        }
    }

    public User getUserById(String id) {
        for (UserListModel user : users) {
            if (user.getUserId().equalsIgnoreCase(id)) {
                return new User(user.getAvatarImage(), id, user.getNickNameString());
            }
        }
        return null;
    }

    public String getUserId(int id) {
        return users.get(id).getUserId();
    }

    public ObservableList getUserList() {
        return users;
    }

    public int getIndexByMessageId(String messageId){
        for (UserListModel user : users) {
            if (user.getUserId().equalsIgnoreCase(messageId)) {
                return users.indexOf(user);
            }
        }return -1;
    }

    public void clear() {
        users.clear();
    }
}
