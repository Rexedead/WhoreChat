/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Client.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Hate
 */
public class MessageListViewController {
    
    @FXML
    private ImageView Avatar;
    
    @FXML
    private Label NickName;
    
    @FXML
    private Label MSG;
    
    @FXML
    private ImageView MsgData;
    
    @FXML
    private HBox root;
    /**
     * Initializes the controller class.
     */
    public void initialize() {}    

    public void setAvatar(Image Avatar) {
        this.Avatar.setImage(Avatar);
    }

    public void setNickName(String NickName) {
        this.NickName.setText(NickName);
    }

    public void setMSG(String MSG) {
        this.MSG.setText(MSG);
    }

    public void setMsgData(Image MsgData) {
        this.MsgData.setImage(MsgData);
    }
    
    public void setFileMSG(){
        MsgData.setVisible(true);
    }

    public HBox getRoot() {
        return root;
    }
    
}
