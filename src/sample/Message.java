/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Hate
 */
public class Message implements Serializable{
    private String message;
    private boolean System;
    private boolean File;
    private File file;

    public Message(String message, boolean System, boolean File, File file) {
        this.message = message;
        this.System = System;
        this.File = File;
        this.file = file;
    }

    public Message(String message, boolean System, boolean File) {
        this.message = message;
        this.System = System;
        this.File = File;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSystem() {
        return System;
    }

    public boolean isFile() {
        return File;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    
    
    
}
