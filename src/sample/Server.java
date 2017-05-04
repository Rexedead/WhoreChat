/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Hate
 */
public class Server{
    private int PORT = 28960;                               //Порт сервера "По умолчанию"
    private ArrayList<Client> clients = 
            new ArrayList<Client>();                        //Массив клиентов
    
    public Server(){
        serverUp();                                         //Конструктор для запуска сервера с портом "По умолчанию"
    }
    
    public Server(int port){
        this.PORT = port;                                   //Конструктор для запуска сервера с заданным портом
        serverUp();
    }
    
    private void serverUp(){                                //Метод запуска сервера(тело сервера)
        ServerSocket server;
        try {
            server = new ServerSocket(PORT);                //Попытка запустить сервер на порте PORT
            System.out.println("Server online");
        } catch (IOException ex) {
            System.out.println
        ("Error #1: Server offline.");
            return;                                         //При неудачном запуске закрыть программу
        }
        
        while(true){
            try {
                Socket serverUser = server.accept();        //Сервер ждёт клиента
                clients.add(new Client(serverUser));        //Подключившийся клиент добавляется в массив клиентов
            } catch (IOException ex) {
                System.out.println
        ("Error #2: Can't connect client.");
            }
        }
    }
    
    private class Client extends Thread{                    //Класс клиентов
        private BufferedReader in = null;                   //Поток для чтения данных от клиента
        private PrintWriter out = null;                     //Поток для отправки данных клиенту
        private Socket clientSocket = null;                 //Сокет клиента
        private String nickname = null;                     //Ник клиента(Будет изменено когда подключим БД)
        
        public Client(Socket clientSocket) throws IOException{
            this.clientSocket = clientSocket;
            try {
            /*    in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));
                out = new PrintWriter(
                        clientSocket.getOutputStream());*/
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String textFromClient;
                textFromClient = in.readLine(); // read the text from client
                System.out.println(textFromClient);


                String textToClient = "Recieved "+textFromClient;
                out.print(textToClient + "\r\n");  // send the response to client
                out.flush();
//                out.close();
//                in.close();



            } catch (IOException ex) {
                System.out.println
        ("Error #3: input/output stream not responding.");
                close();
            }
        }
        
        @Override
        public void run(){
            System.out.println("Client "
                    + clientSocket.toString() 
                    + " cames now");                        //Оповещение о том, что клиент в зашел в чат(серверное)
            String message = "";                            //Строка для сообщений клиента
            while(true){
                try {
                    message = in.readLine();                //Получаем сообщение клиента
                    if(message.substring(0, 4).equals("/rcon")){
//                        switch(message.substring(5)){
//                            case("add"):
//                                break;
//                            case("delete"):
//                                break;
//                            case("nickname"):
//                                break;
//                            case("exit"):
//                                for(Client client: clients){
//                                    if(client.clientSocket == clientSocket){
//                                        clients.remove(client);
//                                    }
//                                }
//                                return;
//                        }
                    }
                    for(Client client : clients){
                        client.out.println(message);        //Отправляем полученное сообщение всем клиентам на сервере
                    }
                } catch (IOException ex) {
                    System.out.println
        ("Error #4: server connection lost.");
                } finally {
                    try {
                        close();
                    } catch (IOException ex) {
                        System.out.println
        ("Error #5: can't close stream.");
                    }
                    
                }
            }
        }
        
        private void close() throws IOException{            //Метод для закрытия потоков чтения и записи клиента
            in.close();
            out.close();
        }
    }
    
}
