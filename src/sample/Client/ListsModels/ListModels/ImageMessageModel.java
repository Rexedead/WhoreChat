/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Client.ListsModels.ListModels;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Hate
 */
public class ImageMessageModel extends MessageListModel{
    private ImageView image = new ImageView();
    
    public ImageMessageModel(){
        image.setFitHeight(40);
        image.setFitWidth(40);
        getChildren().add(image);
    }
    
    public void setImage(Image image){
        this.image.setImage(image);
    }
    
    public Image getImage(){return this.image.getImage();}
}
