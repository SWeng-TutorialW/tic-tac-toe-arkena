package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();

	public SimpleServer(int port) {
		super(port);

	}

	ConnectionToClient playerX;
	ConnectionToClient playerO;
	Boolean[][] boardState = new Boolean[][]{
			{false, false, false},
			{false, false, false},
			{false, false, false}
	};
	String[][] ButtonValue = new String[][]{
			{null, null, null},
			{null, null, null},
			{null, null, null}
	};
	boolean Xturn;

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		if (msgString.startsWith("Move:")) {
			if (Xturn && client == playerX || !Xturn && client == playerO) {
				String SubString = msgString.substring(5);
				String[] parts = SubString.split(",");
				int row = Integer.parseInt(parts[0]);
				int col = Integer.parseInt(parts[1]);
				if (boardState[row][col] == false) {
					String Turn = Xturn ? "X" : "O";
					sendToAllClients("move:" + row + ":" + col + ":" + Turn);
					boardState[row][col] = true;
					ButtonValue[row][col] = Turn;
					String isThereWinner = checkWinner();
					if (isThereWinner != null) {
						sendToAllClients("End the game");
						sendToAllClients("The winner is" + isThereWinner);
					} else {
						Xturn = !Turn.equals("X");
						ConnectionToClient Current = (client == playerX ? playerO : playerX);
						ConnectionToClient NotTurn = (client == playerX ? playerX : playerO);
						try {
							Current.sendToClient("your turn");
							NotTurn.sendToClient("end your turn");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		if (msgString.startsWith("#warning")) {
			Warning warning = new Warning("Warning from server!");
			try {
				client.sendToClient(warning);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (msgString.startsWith("remove client")) {
			if (!SubscribersList.isEmpty()) {
				for (SubscribedClient subscribedClient : SubscribersList) {
					if (subscribedClient.getClient().equals(client)) {
						SubscribersList.remove(subscribedClient);
						break;
					}
				}
			}
		}
		else if (msgString.startsWith("add client")) {
			SubscribedClient newClient = new SubscribedClient(client);
			if (SubscribersList.size() < 2) {
				SubscribersList.add(newClient);
			}
			if (SubscribersList.size() == 2 && playerO == null && playerX == null) {
				sendToAllClients("you can start the game");
			}
		}
		else if (msgString.startsWith("start")) {
			sendToAllClients("turn off the start");
			if (Math.random() < 0.5) {
				playerX = SubscribersList.get(0).getClient();
				playerO = SubscribersList.get(1).getClient();
			} else {
				playerX = SubscribersList.get(1).getClient();
				playerO = SubscribersList.get(0).getClient();
			}
			try {
				Xturn = true;
				playerX.sendToClient("you are player X");
				playerX.sendToClient("your turn");
				playerO.sendToClient("you are player O");
				playerO.sendToClient("end your turn");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}

	public void sendToAllClients(String message) {
		try {
			for (SubscribedClient subscribedClient : SubscribersList) {
				subscribedClient.getClient().sendToClient(message);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public String checkWinner(){
		for (int i = 0; i < 3; i++) {
			if (ButtonValue[i][0] != null &&
					ButtonValue[i][0].equals(ButtonValue[i][1]) &&
					ButtonValue[i][0].equals(ButtonValue[i][2])) {
				return ButtonValue[i][0];
			}
		}

		// בדיקת עמודות
		for (int j = 0; j < 3; j++) {
			if (ButtonValue[0][j] != null &&
					ButtonValue[0][j].equals(ButtonValue[1][j]) &&
					ButtonValue[0][j].equals(ButtonValue[2][j])) {
				return ButtonValue[0][j];
			}
		}

		// בדיקת אלכסון ראשי
		if (ButtonValue[0][0] != null &&
				ButtonValue[0][0].equals(ButtonValue[1][1]) &&
				ButtonValue[0][0].equals(ButtonValue[2][2])) {
			return ButtonValue[0][0];
		}

		if (ButtonValue[0][2] != null &&
				ButtonValue[0][2].equals(ButtonValue[1][1]) &&
				ButtonValue[0][2].equals(ButtonValue[2][0])) {
			return ButtonValue[0][2];
		}

		// לא נמצא מנצח
		return null;
	}
}

