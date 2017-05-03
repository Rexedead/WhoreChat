/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hate
 */
public class Server{
    private int PORT = 28960;                               //Порт сервера "По умолчанию"
    
    public Server(){
        serverUp();                                         //Конструктор для запуска сервера с портом "По умолчанию"
    }
    
    public Server(int port){
        this.PORT = port;                                   //Конструктор для запуска сервера с заданным портом
        serverUp();
    }
    
    private void serverUp(){                                //Метод запуска сервера(тело сервера)
        ServerSocket server;
        try {
            server = new ServerSocket(PORT);                //Попытка запустить сервер на порте PORT
            System.out.println("Server online");
        } catch (IOException ex) {
            System.out.println("Error #1: Server offline");
            return;                                         //При неудачном запуске закрыть программу
        }
        
        while(true){
            
        }
    }
}
