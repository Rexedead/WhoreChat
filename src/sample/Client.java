package sample;


import java.io.*;
import java.net.Socket;

public class Client {

    private Socket connection;
    private PrintWriter out;
    private BufferedReader in;
    private Boolean isConnected = false;
    
    public Client(String serverAddress, int serverPort){}

    public void connect(String serverAddress, int serverPort) {}

    public void disconnect() throws IOException {}
    
    public boolean isConnected(){return isConnected;}

    public void sendMessage(String message) {}

    private String messageUpdater() throws IOException {
        String message = null;
        return message;
    }
}
