package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {

    private static Socket connection;
    static private ObjectOutputStream out;
    static private ObjectInputStream in;
    private static String serverAddress = "127.0.0.1";
    private static int serverPort = 2283;

    public static void main(String[] args) {

    }

    @Override
    public void run() {
        try {
            InetAddress ipAddress = InetAddress.getByName(serverAddress);
            try {
                connection = new Socket(ipAddress, serverPort);
                while (true){
                    out =new ObjectOutputStream(connection.getOutputStream());
                    in = new ObjectInputStream(connection.getInputStream());
                    System.out.println("");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}


