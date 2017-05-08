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
public class User extends ClientData implements Serializable {

    public User(Image avatar,String id, String nickName) {
        super(id, nickName);

    }

    public User(String id, String nickName) {
        super(id, nickName);

    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public String getId() {
        return super.getId();
    }
}
