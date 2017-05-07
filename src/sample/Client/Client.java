package sample.Client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import sample.Message;

public class Client {

    private static Socket connection;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static boolean isConnected = false;
    private static boolean Authorisated = false;
    
    public Client(){}

//    public Client(String serverAddress, int serverPort) {
//        try {
//            this.connection = new Socket(serverAddress, serverPort);
//            in = new ObjectInputStream(connection.getInputStream()); //получаем смс с сервера
//            out = new ObjectOutputStream(connection.getOutputStream()); //поток отправки сообщения
//            isConnected = true;
//        } catch (IOException e) {
//            System.out.println("Не удалось подключиться к серверу");
//        }
//
//    }
    public static void connect(String serverAddress, int serverPort){
        try {
            Client.connection = new Socket(serverAddress, serverPort);
            in = new ObjectInputStream(connection.getInputStream()); //получаем смс с сервера
            out = new ObjectOutputStream(connection.getOutputStream()); //поток отправки сообщения
            isConnected = true;
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void disconnect() throws IOException {
        sendMessage(new Message());
        in.close();
        out.close();
        isConnected = false;
    }

    public static void sendMessage(Message message) throws IOException {
        out.writeObject(message);
    }
    
    public static void sendSystemMessage(Object object) throws IOException {
        out.writeObject(object);
    }

    public static Object messageUpdater() throws IOException, ClassNotFoundException {
        return in.readObject();
    }

    public static boolean isConnected() {
        return isConnected;
    }

    public static boolean isAuthorisated() {
        return Authorisated;
    }
    
    public static void setAuthorisated() {
        Client.Authorisated = true;
    }
}
