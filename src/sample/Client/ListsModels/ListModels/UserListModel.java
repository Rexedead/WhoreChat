/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Client.ListsModels.ListModels;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 * @author Hate
 */
public class UserListModel extends HBox{
    private ImageView Avatar;
    private Label NickName;
    private String UserId;
    
    
    public UserListModel(String UserId, ImageView Avatar, Label NickName){
        this.UserId = UserId;
        this.Avatar.setFitHeight(30);
        this.Avatar.setFitWidth(30);
        this.NickName.setMinWidth(30);
        getChildren().addAll(this.Avatar, this.NickName);
    }
    
    public void setAvatarImage(Image Avatar){
        this.Avatar.setImage(Avatar);
    }
    
    public void setAvatar(ImageView Avatar){
        this.Avatar = Avatar;
    }
    
    public void setNickNameString(String NickName){
        this.NickName.setText(NickName);
    }
    
    public void setNickName(Label nickName){
        this.NickName = nickName;
    }
    
    public String getNickNameString(){return NickName.getText();}
    
    public Label getNickName(){return this.NickName;}
    
    public Image getAvatarImage(){return Avatar.getImage();}
    
    public ImageView getAvatar(){return this.Avatar;}
    
    public void setUserId(String UserId){
        this.UserId = UserId;
    }
    
    public String getUserId(){return this.UserId;}
            
}
