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
public class User {
    private String login;
    private String password;
    private Image avatar;
    private String id;
    private String eMail;
    private String nickName;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, Image avatar, String eMail, String nickName) {
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.eMail = eMail;
        this.nickName = nickName;
    }

    public User(String login, String password, String eMail, String nickName) {
        this.login = login;
        this.password = password;
        this.eMail = eMail;
        this.nickName = nickName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
