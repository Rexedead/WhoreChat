/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Client.ListsModels;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 * @author Hate
 */
public class UserListModel extends HBox{
    private ImageView Avatar = new ImageView();
    private Label NickName = new Label();
    private String UserId;
    
    public UserListModel(String UserId){
        this.UserId = UserId;
        Avatar.setFitHeight(30);
        Avatar.setFitWidth(30);
        NickName.setMinWidth(30);
        getChildren().addAll(Avatar, NickName);
    }
    
    public void setAvatar(Image Avatar){
        this.Avatar.setImage(Avatar);
    }
    
    public void setNickName(String NickName){
        this.NickName.setText(NickName);
    }
    
    public void setUserId(String UserId){
        this.UserId = UserId;
    }
    
    public String getUserId(){return this.UserId;}
            
}
