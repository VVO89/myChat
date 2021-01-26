package server;

public class Message {
    private String nick;
    private String message;

    public String getNick() {
        return nick;
    }

    public void setNick(String clientId) {
        this.nick = clientId;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String meddage) {
        this.message = meddage;
    }
}
