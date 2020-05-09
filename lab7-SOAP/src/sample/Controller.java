package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML public TextField SendTo;
    @FXML public TextArea Message;
    @FXML public TextArea Receiver;
    @FXML public TextArea log;
    @FXML public Button ButtonSend;
    @FXML public Label Title;


    @FXML
    public void ButtonSend(ActionEvent event)
    {



        try {
            Soap.send(Soap.createMessage(Message.getText().replaceAll("\n", System.getProperty("line.separator")),SendTo.getText(),Soap.name));
            log.setText("\nwysylam wiadomosc to:"+SendTo.getText());
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Title.setText(Soap.name+" to "+Soap.to);
        Soap.controller=this;

       //
      //
    }


}
