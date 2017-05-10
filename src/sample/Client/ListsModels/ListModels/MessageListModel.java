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
    private ImageView Avatar = new ImageView();
    private Label nickName = new Label();
    private Label MSG = new Label();

    public MessageListModel() {
        Avatar.setFitHeight(30);
        Avatar.setFitWidth(30);
        nickName.setMinWidth(30);
        MSG.setMaxWidth(250);
        MSG.setWrapText(true);
        getChildren().addAll(Avatar, nickName, MSG);
    }
    
    public void setAvatar(Image avatar){
        this.Avatar.setImage(avatar);
    }
    
    public void setNickName(String nickName){
        this.nickName.setText(nickName);
    }
    
    public void setMessage(String MSG){
        this.MSG.setText(MSG);
    }
}
