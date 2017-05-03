package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {

    private Socket connection;
    private PrintWriter out;
    private BufferedReader in;
    private String serverAddress = "127.0.0.1";
    private int serverPort = 2283;
    
    public Client(){
        
    }
    
    public Client(String ip, int port){
        this.serverAddress = ip;
        this.serverPort = port;
    }

    @Override
    public void run() {
        try {
            InetAddress ipAddress = InetAddress.getByName(serverAddress);
            try {
                connection = new Socket(ipAddress, serverPort);
                while (true){
//                    out =new ObjectOutputStream(connection.getOutputStream());
//                    in = new ObjectInputStream(connection.getInputStream());
//                    out.writeObject("Recieved!");
//
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String answer = in.readLine();
                    System.out.println(answer);


                }
            } catch (IOException e) {
                System.out.println("Error #1: Server not responding!");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String message){
        try {
            out = new PrintWriter(connection.getOutputStream());
            out.println(message);
            out.flush();
            out.close();
        } catch (IOException ex) {
            System.out.println("Error #3: ");
        }
    }
}


