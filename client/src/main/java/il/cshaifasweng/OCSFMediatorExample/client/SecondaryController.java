package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class SecondaryController {


    @FXML // fx:id="host1"
    private TextField host1; // Value injected by FXMLLoader

    @FXML // fx:id="port1"
    private TextField port1; // Value injected by FXMLLoader

    @FXML // fx:id="secondaryButton"
    private Button secondaryButton; // Value injected by FXMLLoader

    @FXML
    void host(ActionEvent event) {

    }

    @FXML
    void port(ActionEvent event) {

    }
    private SimpleClient client = null;
    @FXML
    private void switchToPrimary() throws IOException {
        try{
            String host = host1.getText().trim();
            String port = port1.getText().trim();
            int NumberPort;
            NumberPort = Integer.parseInt(port);
            if(NumberPort < 1 || NumberPort > 65535){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid port number");
                alert.showAndWait();
                return;
            }
            client = SimpleClient.getClient(host, NumberPort);
            client.openConnection();
            Stage stage =(Stage)host1.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("primary.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 572);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage());
            alert.showAndWait();
        }

    }
}
