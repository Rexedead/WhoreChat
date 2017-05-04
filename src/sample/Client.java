package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {

    private Socket connection;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
    private String serverAddress = "127.0.0.1";
    private int serverPort = 28960;

    @Override
    public void run() {
        try {
            InetAddress ipAddress = InetAddress.getByName(serverAddress);
            try {
                connection = new Socket(ipAddress, serverPort); //connect to server
                System.out.println("Connected");
                while (true){

                    out = new PrintWriter(connection.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String textToServer=read.readLine();  //text for server
                    out.print(textToServer + "\r\n" );  // send to server
                    out.flush();
                    System.out.println(in.readLine()); // read from server

//                    in.close();
//                    read.close();

                }
            } catch (IOException e) {
                System.out.println("IO error");
            }
        } catch (UnknownHostException e) {
            System.out.println("Can't connect to server");
        }
    }
}


