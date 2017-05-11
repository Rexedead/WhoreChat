/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import javafx.scene.image.Image;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Hate
 */
public class Message implements Serializable{
    private String message;
    private String id;
    MessageType MessageType;
    private File file;
    private Image img;

    public Message() {
        this.MessageType = MessageType.EXIT;
    }

    public Message(MessageType MessageType) {
        this.MessageType = MessageType;
    }

    public Message(String message, File file, MessageType messageType) {
        this.message = message;
        this.file = file;
        this.MessageType = MessageType;
    }
    
    public Message(String message, Image img, MessageType messageType) {
        this.message = message;
        this.img = img;
        this.MessageType = MessageType;
    }

    public Message(String message) {
        this.message = message;
        this.MessageType = MessageType.MESSAGE;
    }
    
    public Message(String message, String id) {
        this.message = message;
        this.id = id;
        this.MessageType = MessageType.WHISPER;
    }
    
    public Message(String id, MessageType MessageType) {
        this.id = id;
        this.MessageType = MessageType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public MessageType getMessageType(){
        return MessageType;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
    
    
    
}
