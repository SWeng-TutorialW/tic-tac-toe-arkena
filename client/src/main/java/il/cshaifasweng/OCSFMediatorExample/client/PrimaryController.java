package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Platform;
import java.io.IOException;
import org.greenrobot.eventbus.Subscribe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.greenrobot.eventbus.EventBus;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PrimaryController {
	private Button[][] boardButtons;
	@FXML
    void sendWarning(ActionEvent event) {
    	try {
			SimpleClient.getClient().sendToServer("#warning");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


	@Subscribe
	public void handleTurnMessage(TurnMessage msg) {
		Platform.runLater(() -> {
			setBoardEnabled(msg.myTurn);
		});
	}

	@Subscribe
	public void handleTurnButtons(Move move){
		Platform.runLater(()->{
           boardButtons[move.getRow()][move.getCol()].setText(move.getTurn());
		});
	}
	/**
	 * Sample Skeleton for 'primary.fxml' Controller Class
	 */

		@FXML // fx:id="Bottun00"
		private Button Bottun00; // Value injected by FXMLLoader

		@FXML // fx:id="Button01"
		private Button Button01; // Value injected by FXMLLoader

		@FXML // fx:id="Button02"
		private Button Button02; // Value injected by FXMLLoader

		@FXML // fx:id="Button10"
		private Button Button10; // Value injected by FXMLLoader

		@FXML // fx:id="Button11"
		private Button Button11; // Value injected by FXMLLoader

		@FXML // fx:id="Button12"
		private Button Button12; // Value injected by FXMLLoader

		@FXML // fx:id="Button20"
		private Button Button20; // Value injected by FXMLLoader

		@FXML // fx:id="Button21"
		private Button Button21; // Value injected by FXMLLoader

		@FXML // fx:id="Button22"
		private Button Button22;  // Value injected by FXMLLoader


	    @FXML // fx:id="StartB"
	    private Button StartB; // Value injected by FXMLLoader

     	@FXML // fx:id="Text"
    	private TextField Text; // Value injected by FXMLLoader


		@FXML
		void Bottun00(ActionEvent event) {
			try {
				SimpleClient.getClient().sendToServer("Move:0,0");
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}

		@FXML
		void Button01(ActionEvent event) {
			try {
				SimpleClient.getClient().sendToServer("Move:0,1");
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}

		@FXML
		void Button02(ActionEvent event) {
			try {
				SimpleClient.getClient().sendToServer("Move:0,2");
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}

		@FXML
		void Button10(ActionEvent event) {
			try {
				SimpleClient.getClient().sendToServer("Move:1,0");
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}

		@FXML
		void Button11(ActionEvent event) {
			try {
				SimpleClient.getClient().sendToServer("Move:1,1");
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}

		@FXML
		void Button12(ActionEvent event) {
			try {
				SimpleClient.getClient().sendToServer("Move:1,2");
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}

		@FXML
		void Button20(ActionEvent event) {
			try {
				SimpleClient.getClient().sendToServer("Move:2,0");
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}

		@FXML
		void Button21(ActionEvent event) {
			try {
				SimpleClient.getClient().sendToServer("Move:2,1");
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}

		@FXML
		void Button22(ActionEvent event) {
			try {
				SimpleClient.getClient().sendToServer("Move:2,2");
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}

	    @FXML
    	void SetTheWinner(ActionEvent event) {}

	    @FXML
	    void StartB(ActionEvent event) {
			try {
				SimpleClient.getClient().sendToServer("start");
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
	@Subscribe
	public void setTheWinner(String msg){
		Platform.runLater(()->{
			Text.setText(msg);
		});
	}
	@FXML
	void initialize(){
		setBoardEnabled(false);
		StartB.setDisable(true);
		EventBus.getDefault().register(this);
		try {
			SimpleClient.getClient().sendToServer("add client");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boardButtons = new Button[][]{
				{ Bottun00, Button01, Button02 },
				{ Button10, Button11, Button12 },
				{ Button20, Button21, Button22 }
		};
	}
	public void setBoardEnabled(boolean enabled){
		Bottun00.setDisable(!enabled);
		Button01.setDisable(!enabled);
		Button02.setDisable(!enabled);
		Button10.setDisable(!enabled);
		Button11.setDisable(!enabled);
		Button12.setDisable(!enabled);
		Button20.setDisable(!enabled);
		Button21.setDisable(!enabled);
		Button22.setDisable(!enabled);

	}
	@Subscribe
	public void StartTheGame(Boolean start){
		Platform.runLater(()->{
			StartB.setDisable(start);
		});
	}
}
