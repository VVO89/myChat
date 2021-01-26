package client;

public interface ServerService {
    void openConnection();
    void closeConnection();

    void sendMessage(String message);
    String readMessages();

}
