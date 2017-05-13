/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.Serializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Hate
 */
public class User extends ClientData implements Serializable {
    private Label nickName = new Label();
    private ImageView Avatar = new ImageView();
    
    public User(Image avatar,String id, String nickName) {
        super(id);
        this.nickName.setText(nickName);
        this.Avatar.setImage(avatar);
    }

    public User(String id, String nickName) {
        super(id);
        this.nickName.setText(nickName);
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public String getId() {
        return super.getId();
    }

    public Label getNickName() {
        return nickName;
    }

    @Override
    public void setNickName(String nickName) {
        this.nickName.setText(nickName);
    }
    
    public void setNullAvatar(){
        this.Avatar.setImage(null);
    }

    public ImageView getAvatar() {
        return Avatar;
    }
    
    @Override
    public Image getAvatarImage(){return Avatar.getImage();}

    @Override
    public void setAvatar(Image Avatar) {
        this.Avatar.setImage(Avatar);
    }
    
}
