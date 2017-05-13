/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.Serializable;
import javafx.scene.image.Image;

/**
 *
 * @author Hate
 */
public class ClientData implements Serializable {

    private String eMail;
    private String password;
    private Image avatar;
    private String id;
    private String nickName;
    private boolean signUp = false;

    public ClientData(String nickName, String password) {
        this.nickName = nickName;
        this.password = password;
    }
    
    public ClientData(String id){
        this.id = id;
    }

    public ClientData(Image avatar, String id, String nickName) {
        this.avatar = avatar;
        this.id = id;
        this.nickName = nickName;
    }

    public ClientData(String password, Image avatar, String eMail, String nickName) {
        this.password = password;
        this.avatar = avatar;
        this.eMail = eMail;
        this.nickName = nickName;
        signUp = true;
    }

    public ClientData(String password, String eMail, String nickName) {
        this.password = password;
        this.eMail = eMail;
        this.nickName = nickName;
        signUp = true;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSignUp() {
        return signUp;
    }

    public Image getAvatarImage() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getNickNameString() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    
}
