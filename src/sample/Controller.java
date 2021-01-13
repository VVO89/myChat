package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {

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
        txGeneralWindow.setText(txGeneralWindow.getText().concat("\n").concat(txMessageUser.getText()));
    }

}
