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
public class MessageListModel extends HBox{
    private ImageView Avatar;
    private Label nickName;
    private Label MSG = new Label();

    public MessageListModel(ImageView Avatar, Label nickName, String MSG) {
        this.Avatar = Avatar;
        this.nickName = nickName;
        this.MSG.setText(MSG);
        this.Avatar.setFitHeight(30);
        this.Avatar.setFitWidth(30);
        this.nickName.setMinWidth(30);
        this.MSG.setMaxWidth(250);
        this.MSG.setWrapText(true);
        getChildren().addAll(this.Avatar, this.nickName, this.MSG);
    }
    
    public void setAvatarImage(Image avatar){
        this.Avatar.setImage(avatar);
    }
    
    public void setNickNameString(String nickName){
        this.nickName.setText(nickName);
    }
    
    public void setMessage(String MSG){
        this.MSG.setText(MSG);
    }

    public void setAvatar(ImageView Avatar) {
        this.Avatar = Avatar;
    }

    public void setNickName(Label nickName) {
        this.nickName = nickName;
    }
    
    
}
