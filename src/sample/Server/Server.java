/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Server;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sample.ClientData;
import sample.Message;
import sample.MessageType;
import sample.User;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
    
    public synchronized void AddUserInTheList(User user){
        Users.add(user);
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

            new DBworker().writeToSQLwhenRegister(new ClientData("pw","22226@","lg"));

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
                //clients.add(client);                        //Подключившийся клиент добавляется в массив клиентов
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
        String clientId = null;
        
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
                autorisationCheck();
                out.writeObject(Users);
                sendMeToOthersClients(new User(ClientData.getAvatar(), clientId, ClientData.getNickName()));
                ClientData = null;
            } catch (IOException | ClassNotFoundException | ClassCastException ex) {
                try {
                    close();
                } catch (IOException ex1) {}           
            }
            try {
                while(true){
                    message = (Message) in.readObject();                                    //Получаем сообщение клиента
                    try {
                        messageTypeAction(message);
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
            clients.remove(this);
            sendToAllUsers(new Message(clientId, MessageType.USEROFFLINE));
            in.close();
            for(int i = 0; i < Users.size(); i++){
                if(Users.get(i).getId() == clientId){
                    Users.remove(i);
                }
            }
            clients.remove(this);
            interrupt();
        }
        
        private void saveAvatar(Image img){
            File outputFile = new File(clientId);
            BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);
            try {
              ImageIO.write(bImage, "png", outputFile);
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
        }
        
        private void sendToAllUsers(Object object){
            for (Client client : clients) {
                try {
                    client.out.writeObject(object);
                } catch (IOException ex) {
                    
                }
            }
        }
        
        private void sendMeToOthersClients(User user){
            for (Client client : clients) {
                if(client != this){
                    try {
                        client.out.writeObject(user);
                    } catch (IOException ex) {

                    }
                }
            }
        }
        
        private void autorisationCheck() throws IOException, ClassNotFoundException{
            while(true){
                    ClientData = (ClientData)in.readObject();
                    if(ClientData.isSignUp()){
                        clientId = new DBworker().writeToSQLwhenRegister(ClientData);
                            if(clientId.endsWith("exist") || clientId.equals("noDBconnect")){
                                if(clientId.equals("noDBconnect")){
                                    out.writeObject(new Message("Try again later", MessageType.MESSAGE));
                                    close();
                                }else{
                                    out.writeObject(new Message(clientId, MessageType.MESSAGE));
                                }
                            }else{
                                AddUserInTheList(new User(null, clientId,ClientData.getNickName()));
                                clients.add(this);
                                out.writeObject(new Message(clientId,MessageType.AUTHORISATION));
                                break;
                            }
                    }else{
                        clientId = new DBworker().readFromSQLwhenLogining(ClientData.getNickName(), ClientData.getPassword());
                        if(clientId.equals("invalid") || clientId.equals("noDBconnect")){
                            if(clientId.equals("noDBconnect")){
                                out.writeObject(new Message("Try again later", MessageType.MESSAGE));
                                close();
                            }else{
                                out.writeObject(new Message(clientId, MessageType.MESSAGE));
                            }
                        }else{
                            AddUserInTheList(new User(null, clientId,ClientData.getNickName()));
                            clients.add(this);
                            out.writeObject(new Message(clientId, MessageType.AUTHORISATION));
                            break;
                        }
                    }
                }
        }
        
        private void messageTypeAction(Message message) throws IOException{
            switch(message.getMessageType()){
                            case MESSAGE:
                                System.out.println("clientID "+clientId+": Message: "+ message.getMessage());
                                message.setId(clientId);
                                sendToAllUsers(message);
                                break;
                            case WHISPER:
                                for (Client client : clients) {
                                    if(client.getID().equals(message.getId())){
                                        message.setId(clientId);
                                        client.out.writeObject(message);
                                    }
                                }
                                break;
                            case FILEMESSAGE:
                                System.out.println("FileMessage from client: "+message.getMessage());
                                message.setId(clientId);
                                sendToAllUsers(message);
                                break;
                            case AVATAR:
                                saveAvatar(message.getImg());
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
        }
        
        private String getID(){return clientId;}
    }
    
}
