package sample;


import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private Socket connection;
    private PrintWriter out;
    private BufferedReader in;
    InputStream inputStream;
    private boolean connectWithStart;
    private String serverAddress;
    private int serverPort;

    private void setupProp() throws IOException {
        Properties properties = new Properties();
        String propFilename = "config.properties";
        inputStream = getClass().getClassLoader().getResourceAsStream(propFilename);

        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFilename + "' not found in the classpath");
        }

        // get the property value and print it out
        this.serverAddress = properties.getProperty("IP");
        this.serverPort = Integer.parseInt(properties.getProperty("port"));
        this.connectWithStart = Boolean.parseBoolean(properties.getProperty("AutoConnect"));
        autoFillServerIPPort();
        if(connectWithStart){
            connect(this.serverAddress, this.serverPort);
        }
    }

    private void messageUpdater() throws IOException {
        while (true) {
            try {
                in = new BufferedReader(                                        //Поток для получения данных с сервера
                        new InputStreamReader(connection.getInputStream()));
                String answer = in.readLine();
                if (answer == null) {                                             //Если соббщение равно null, то делаем дисконнект, так как сервер более
                    disconnect();                                               //не отвечает
                    break;
                }

                MessageList.appendText(answer + "\n");                          //Выводим полученное сообщение на экран
            } catch (SocketException e) {
                disconnect();
                break;
            }
        }
    }

    public void disconnect() throws IOException {
        sendMessage("/rcon exit");                                              //Отсылаем серверу каманду на отключение
        MessageList.appendText("Error: Connection lost.\n");                    //Отображаем на экране что соединение прервано
        ConnectButton.setDisable(false);                                        //Делаем кнопку Connect снова доступной
        in.close();                                                             //Закрываем птоки чтения и записи
        out.close();
    }

    public void connect() {
        ConnectButton.setDisable(true);                                         //Делаем кнопку Connect недоступной
        String socket = SocketInputArea.getText();                              //Получаем соккет введёный пользователем
        if (socket.length() > 0 && socket.lastIndexOf(":") > 0) {
            try {
                connect(socket.substring(0, socket.lastIndexOf(":")),           //Разделяем соккет на ip и port
                        Integer.parseInt(
                                socket.substring(socket.lastIndexOf(":") + 1)));
            } catch (NumberFormatException e) {
                MessageList.appendText("Error: Invalid port.\n");
                ConnectButton.setDisable(false);
            }
        } else {
            MessageList.appendText("Error: Invalid socket.\n");
            ConnectButton.setDisable(false);
        }
    }

    public void connect(String serverAddress, int serverPort) {
        try {
            InetAddress ipAddress = InetAddress.getByName(serverAddress);       //Приводим строку с ip в вид InetAddress
            this.connection = new Socket(ipAddress, serverPort);                //Задаём сокет для подключения
            new Thread(new Runnable() {

                @Override
                public void run() {                                             //Запускаем метод для получения сообщений с сервера. Делаем это в
                    try {                                                       //отдельном потоке, чтобы не лагал GUI
                        messageUpdater();
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        } catch (UnknownHostException ex) {
            MessageList.appendText("Error: Host not responding.\n");
            ConnectButton.setDisable(false);
        } catch (IOException ex) {
            MessageList.appendText("Error: Connection lost.\n");
            ConnectButton.setDisable(false);
        } catch (IllegalArgumentException e) {
            MessageList.appendText("Error: Invalid ip or port.\n");
            ConnectButton.setDisable(false);
        }
    }

    public void sendMessageViaKeyboard() {                                           //Метод отправки сообщения через Enter

        SendTextArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {                     //TODO: отправляется только после второго нажатия
                sendMessage(SendTextArea.getText());
            }
        });
    }

    public void sendMessage() {

        sendMessage(SendTextArea.getText());                             //Метод отправки сообщения кнопкой в GUI
    }

    public void sendMessage(String message) {                                    //Метод отправки сообщения. Может быть использован для общения
        try {                                                                   //программы с сервером. Например для кнопки "Добавить в друзья"
            out = new PrintWriter(connection.getOutputStream(), true);                //Открываем поток для отправки сообщений
            out.println(message);                                               //Отправляем сообщение серверу
//            out.flush();                                                        //Принуждает выходной буфер отправить сообщение
            this.SendTextArea.setText("");                                          //Очищаем отправленную строку
        } catch (IOException ex) {
            System.out.println("Error #3: ");
        } catch (NullPointerException e) {
            MessageList.appendText("Error: You are not connected to server.\n");
        }
    }


    public void autoFillServerIPPort() {
        this.SocketInputArea.setText(this.serverAddress + ":" + this.serverPort);  //Заполняем сервер:порт из conf.epic
    }

}
