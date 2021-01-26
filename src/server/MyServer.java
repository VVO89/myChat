package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    public static final int PORT = 8081;
    private List<ClientHandler> clients;
    private AuthService authService;

    public MyServer() {
        // работа с клиентами
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
           while(true){
               System.out.println("Ожидаем подключение клиентов");
               Socket socket = serverSocket.accept();
               System.out.println("Клиент подключился");
               new ClientHandler(this,socket);
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (authService != null) {
                authService.stop();
            }
        }

    }

    // отправка сообщений всем доступным клиентам
    public synchronized void broadcastMessage(Message message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public synchronized boolean isNickBusy(String nick){
        for (ClientHandler client : clients) {
            if(nick.equals(client.getNick())){
                return true;
            }
        }
        return false;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler);
    }
}
