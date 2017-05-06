/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private ArrayList<User> Users = new ArrayList<User>();
    private ArrayList<Client> clients = 
            new ArrayList<Client>();                        //Массив клиентов
    
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
            DBworker ddd = new DBworker();


//            new DBworker().writeToSQLwhenRegister(new ClientData("pw","222@","lg"));

        } catch (IOException ex) {
            System.out.println
        ("Error #1: Server offline.");
            return;                                         //При неудачном запуске закрыть программу
        }
        
        while(true){
            try {
                Socket serverUser = server.accept();        //Сервер ждёт клиента
                System.out.println("Client connected" + serverUser.toString());
                Client client = new Client(serverUser);
                clients.add(client);                        //Подключившийся клиент добавляется в массив клиентов
                client.start();
            } catch (IOException ex) {
                System.out.println
        ("Error #2: Can't connect client.");
            }
        }
    }
    
    private class Client extends Thread{                    //Класс клиентов
        private ObjectInputStream in = null;                   //Поток для чтения данных от клиента
        private ObjectOutputStream out = null;                     //Поток для отправки данных клиенту
        private Socket clientSocket = null;                 //Сокет клиента
        private String nickname = null;                     //Ник клиента(Будет изменено когда подключим БД)
        private ClientData ClientData;
        
        public Client(Socket clientSocket) throws IOException{
            this.clientSocket = clientSocket;
            try {
                
                this.out = new ObjectOutputStream(clientSocket.getOutputStream());
                this.in = new ObjectInputStream(clientSocket.getInputStream());
                        
            } catch (IOException ex) {
                System.out.println
        ("Error #3: input/output stream not responding.");
                close();
            }
        }
        
        @Override
        public void run(){
            System.out.println("Client "
                    + clientSocket.toString() 
                    + " cames now");                                            //Оповещение о том, что клиент в зашел в чат(серверное)
            Message message;                                                    //Строка для сообщений клиента 
            try {
                ClientData = (ClientData)in.readObject();
                if(ClientData.isSingUp()){
                    new DBworker().writeToSQLwhenRegister(ClientData);
                }else{
                   Users.add(new User(setId(new DBworker().readFromSQLwhenLogining(ClientData.geteMail(), ClientData.getPassword())));
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                while(true){
                    message = (Message) in.readObject();                                    //Получаем сообщение клиента
                    
                    try {
                        switch(message.getMessageType()){
                            case MESSAGE:
                                System.out.println(message.getMessage());
                                message.setId(ClientData.getId());
                                for (Client client : clients) {
                                    client.out.writeObject(message); //Отправляем полученное сообщение всем клиентам на сервере
                                }
                                break;
                            case WHISPER:
                                break;
                            case FILEMESSAGE:
                                break;
                            case AVATAR:
                                break;
                            case ADDFRIEND:
                                break;
                            case DELFRIEND:
                                break;
                            case NICKNAME:
                                break;
                            case EXIT:
                                close();
                                break;
                        }
                    } catch (NullPointerException e) {
                        
                    }
                }
            } catch (IOException ex) {
                System.out.println
    ("Error #4: Client connection lost.");
            } catch (ClassNotFoundException ex) {
                System.out.println
        ("Error #5: Class not found.");
            } finally {
                try {
                    close();
                } catch (IOException ex) {
                    System.out.println
    ("Error #6: can't close stream.");
                }

            }
        }
        
        private void close() throws IOException{            //Метод для закрытия потоков чтения и записи клиента
            in.close();
            out.close();
            for(int i = 0; i < Users.size(); i++){
                if(Users.get(i).getId() == ClientData.getId()){
                    Users.remove(i);
                }
            }
            clients.remove(this);
            interrupt();
        }
    }
    
}
