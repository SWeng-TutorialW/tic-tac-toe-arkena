/**
 * Sample Skeleton for 'First.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class First {

    @FXML // fx:id="HostId"
    private TextField HostId; // Value injected by FXMLLoader

    @FXML // fx:id="PortId"
    private TextField PortId; // Value injected by FXMLLoader

    @FXML // fx:id="Start"
    private Button Start; // Value injected by FXMLLoader

    @FXML
    void StartGame(ActionEvent event) {
         try{
             String host = HostId.getText();
             int port = Integer.parseInt(PortId.getText());
             SimpleClient client = SimpleClient.getClient(host, port);
             javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("primary.fxml"));
             javafx.scene.Parent root = loader.load();
             javafx.scene.Scene scene = new javafx.scene.Scene(root);
             javafx.stage.Stage stage = (javafx.stage.Stage) Start.getScene().getWindow();
             stage.setScene(scene);
             stage.show();
         }
         catch (Exception e){
             e.printStackTrace();
         }
    }

    @FXML
    void WriteHost(ActionEvent event) {
    }

    @FXML
    void WritePort(ActionEvent event) {
    }

}
