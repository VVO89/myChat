package server;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private Socket socket;
    private MyServer myServer;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String nick;

    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());

            // создаем поток для работы с клиентом
            new Thread(() -> {
                try {
                    authentification();
                    readMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
        }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        myServer.unsubscribe(this);
        Message message = new Message();
        message.setmessage(nick.concat(" вышел из чата"));
        myServer.broadcastMessage(message);
        try {
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void authentification() {
        while (true){
            try {
                AuthMessage message = new Gson().fromJson(dataInputStream.readUTF(),AuthMessage.class);
                String nick = myServer.getAuthService().getNickByLoginAndPass(message.getLogin(), message.getPassword());
                if(nick != null && !myServer.isNickBusy(nick)){
                    message.setAuthentificated(true);
                    dataOutputStream.writeUTF(new Gson().toJson(message));
                    Message broadcastMsg = new Message();
                    broadcastMsg.setmessage("nick".concat(" вошел в чат"));
                    myServer.broadcastMessage(broadcastMsg);
                    myServer.subscribe(this);
                    this.nick = nick;
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void readMessage() throws IOException {
        while (true) {
            // подключаем внешнюю библиотеку Gson
            // считываем строку
            Message message = new Gson().fromJson(dataInputStream.readUTF(), Message.class);
            // отправка сообщений всем доступным клиентам
            System.out.println(message);
            if("/end".equals(message.getmessage())){
                return;
            }
            myServer.broadcastMessage(message);
        }
    }

    public void sendMessage(Message message) {
        try {
            dataOutputStream.writeUTF(new Gson().toJson(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }
}
