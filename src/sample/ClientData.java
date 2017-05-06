/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import javafx.scene.image.Image;

/**
 *
 * @author Hate
 */
public class ClientData {
    private String eMail;
    private String password;
    private Image avatar;
    private String id;
    private String nickName;

    public ClientData(String eMail, String password) {
        this.eMail = eMail;
        this.password = password;
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
    }

    public ClientData(String password, String eMail, String nickName) {
        this.password = password;
        this.eMail = eMail;
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Image getAvatar() {
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

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    
}
