/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Client.ListsModels.ListModels;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Hate
 */
public class FileMessageModel extends MessageListModel{
    private ImageView MSGData = new ImageView();
    private File DATA;
    
    public FileMessageModel(){
        MSGData.setFitHeight(20);
        MSGData.setFitWidth(20);
        getChildren().add(MSGData);
    }
    
    public void setDATA(File DATA){
        this.DATA = DATA;
        MSGData.setImage(new Image("/sample/resources/FileMessageIcon.png"));
    }
    
    public File getDATA(){return DATA;}
}
