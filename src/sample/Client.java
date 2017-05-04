package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {

    private static Socket connection;
    static private ObjectOutputStream out;
    static private ObjectInputStream in;
    private static String serverAddress = "127.0.0.1";
    private static int serverPort = 28960;

    @Override
    public void run() {
        try {
            InetAddress ipAddress = InetAddress.getByName(serverAddress);
            try {
                connection = new Socket(ipAddress, serverPort);
                while (true){
////                   out =new ObjectOutputStream(connection.getOutputStream());
////                   in = new ObjectInputStream(connection.getInputStream());
////                   out.writeObject("Recieved!");

                    BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String answer = input.readLine();
                    System.out.println(answer);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}


