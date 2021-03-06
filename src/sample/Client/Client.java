package sample.Client;


import sample.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private Socket connection;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean isConnected = false;
    
    public Client(){}

    public Client(String serverAddress, int serverPort) {
        try {
            this.connection = new Socket(serverAddress, serverPort);
            in = new ObjectInputStream(connection.getInputStream()); //получаем смс с сервера
            out = new ObjectOutputStream(connection.getOutputStream()); //поток отправки сообщения
            isConnected = true;
        } catch (IOException e) {
            System.out.println("Не удалось подключиться к серверу");
        }

    }
    public void connect(String serverAddress, int serverPort){
        try {
            this.connection = new Socket(serverAddress, serverPort);
            in = new ObjectInputStream(connection.getInputStream()); //получаем смс с сервера
            out = new ObjectOutputStream(connection.getOutputStream()); //поток отправки сообщения
            isConnected = true;
        } catch (IOException ex) {
            System.out.println("Не удалось подключиться к серверу");
        }
    }
    public void disconnect(){
        try{
            isConnected = false;
            sendMessage(new Message());
            connection = null;
            in.close();
            out.close();
        }catch(NullPointerException | IOException e){
        }
    }

    public void sendMessage(Message message) throws IOException {
        out.writeObject(message);
    }
    
    public void sendSystemMessage(Object object) throws IOException {
        out.writeObject(object);
    }

    public Object messageUpdater() throws IOException, ClassNotFoundException {
        return in.readObject();
    }

    public boolean isConnected() {
        return isConnected;
    }
}
