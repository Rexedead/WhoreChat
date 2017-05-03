/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.System.in;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hate
 */
public class Server{
    private int PORT = 28960;                               //Порт сервера "По умолчанию"
    private ArrayList<Client> clients = new ArrayList<Client>();
    
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
            System.out.println("Error #1: Server offline.");
            return;                                         //При неудачном запуске закрыть программу
        }
        
        while(true){
            try {
                Socket serverUser = server.accept();
                clients.add(new Client(serverUser));
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private class Client extends Thread{
        private BufferedReader in = null;
        private PrintWriter out = null;
        private Socket clientSocket = null;
        private String nickname = null;
        
        public Client(Socket clientSocket) throws IOException{
            this.clientSocket = clientSocket;
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream());
            } catch (IOException ex) {
                System.out.println("Error #2: input/output stream not responding.");
                close();
            }
        }
        
        @Override
        public void run(){
            System.out.println("Client " + clientSocket.toString() + " cames now");
            String message = "";
            while(true){
                try {
                    message = in.readLine();
                    for(Client client : clients){
                        client.out.println(message);
                    }
                } catch (IOException ex) {
                    System.out.println("Error #3: server connection lost.");
                } finally {
                    try {
                        close();
                    } catch (IOException ex) {
                        System.out.println("Error #4: can't close stream.");
                    }
                    
                }
            }
        }
        
        private void close() throws IOException{
            in.close();
            out.close();
        }
    }
    
}
