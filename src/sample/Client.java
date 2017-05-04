package sample;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private Socket connection;
    private PrintWriter out;
    private BufferedReader in;
    private Boolean isConnected = false;

    public Client(String serverAddress, int serverPort) {
        try {
            this.connection = new Socket(serverAddress, serverPort);
            connect(serverAddress,serverPort);
        } catch (IOException e) {
            System.out.println("Не удалось подключиться к серверу");
        }

    }

    public void connect(String serverAddress, int serverPort) {
        try {
            InetAddress ipAddress = InetAddress.getByName(serverAddress);       //Строка с адресом конвертируется в адрес для коннекта
            this.connection = new Socket(ipAddress, serverPort);                //задаем адрес и порт для коннекта
        } catch (UnknownHostException e) {
            System.out.println("Хост не найден");
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }

    public void disconnect() throws IOException {
        sendMessage("/rcon exit");
        in.close();
        out.close();
    }

    public boolean isConnected() {
        return isConnected;         //проверка на коннект
    }

    public void sendMessage(String message) {
        try {
            out = new PrintWriter(connection.getOutputStream()); //поток отправки сообщения
            out.println();  //отправка сообщения
        } catch (IOException e) {
            System.out.println("IO error");
        }


    }

    private String messageUpdater() throws IOException {
        in = new BufferedReader(new InputStreamReader(connection.getInputStream())); //получаем смс с сервера
        String message = in.readLine();     //выводим смс
        return message;
    }
}
