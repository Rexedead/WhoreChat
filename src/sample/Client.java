package sample;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private Socket connection;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Message message;
    private boolean isConnected = false;
    
    public Client(){};

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

    public void disconnect() throws IOException {
        sendMessage(new Message("exit", true, false));
        in.close();
        out.close();
        isConnected = false;
    }

    public void sendMessage(Message message) throws IOException {
        out.writeObject(message);
    }

    public String messageUpdater() throws IOException, ClassNotFoundException {
        message = (Message)in.readObject();            
        return message.getMessage();
    }

    boolean isConnected() {
        return isConnected;
    }
}
