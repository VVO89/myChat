package sample;

import client.ServerService;
import client.SocketServerService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {

    private ServerService serverService;

    public TextField txMessageUser;
    public TextArea txGeneralWindow;
    public Button buttSend;

    public void SendingMessageUser(ActionEvent actionEvent) {
        sendingMessage();
    }

    public void userEnterPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            sendingMessage();
        }
    }

    public void sendingMessage(){
       // txGeneralWindow.setText(txGeneralWindow.getText().concat("\n").concat(txMessageUser.getText()));
        serverService = new SocketServerService();
        serverService.openConnection();
        new Thread(() -> {
            while (true){
                printToUI(txGeneralWindow,serverService.readMessages());
            }
        }).start();
    }

    private void printToUI(TextArea txGeneralWindow, String message){
        txGeneralWindow.appendText(message);
    }

}
