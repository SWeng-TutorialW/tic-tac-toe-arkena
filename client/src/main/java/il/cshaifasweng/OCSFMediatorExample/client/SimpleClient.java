package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.scene.control.Button;
import org.greenrobot.eventbus.EventBus;
import il.cshaifasweng.OCSFMediatorExample.client.TurnMessage;
import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		String msgString = msg.toString();
		if (msg.getClass().equals(Warning.class)) {
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}
		else{
			String message = msg.toString();
			System.out.println(message);
		}
		if(msgString.startsWith("end your turn")){
			EventBus.getDefault().post(new TurnMessage(false));
		}
		else if(msgString.startsWith("your turn")){
			EventBus.getDefault().post(new TurnMessage(true));
		}
		else if (msgString.startsWith("move:")){
			String[] parts = msgString.substring(5).split(":");
			int row = Integer.parseInt(parts[0]);
			int col = Integer.parseInt(parts[1]);
			String turn = parts[2];
			Move move = new Move(row, col, turn);
			EventBus.getDefault().post(move);
		}
		else if (msgString.startsWith("End the game")){
			EventBus.getDefault().post(new TurnMessage(false));
		}
		else if(msgString.startsWith("The winner is")){
			EventBus.getDefault().post(msgString);
		}
		else if(msgString.startsWith("you can start the game")){
			EventBus.getDefault().post(false);
		}
		else if(msgString.startsWith("turn off the start")){
			EventBus.getDefault().post(true);
		}
	}
	
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

}
