package sample;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket connection;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String serverAddress, int serverPort) {
        try {
            this.connection = new Socket(serverAddress, serverPort);
            in = new BufferedReader(new InputStreamReader(connection.getInputStream())); //получаем смс с сервера
            out = new PrintWriter(connection.getOutputStream(), true); //поток отправки сообщения
        } catch (IOException e) {
            System.out.println("Не удалось подключиться к серверу");
        }

    }

    public void disconnect() throws IOException {
        sendMessage("/rcon exit");
        in.close();
        out.close();
        connection.close();
    }

    public boolean isConnected() {
        return connection.isConnected();         //проверка на коннект
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String messageUpdater() throws IOException {
        String message;
        message = in.readLine();
        if(message == null){
            disconnect();
            message = "Connection lost";
        }
            
        return message;
    }
}
