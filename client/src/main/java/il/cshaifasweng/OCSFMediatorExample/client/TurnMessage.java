package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.Serializable;

public class TurnMessage implements Serializable {
    public boolean myTurn;

    public TurnMessage(boolean myTurn){
        this.myTurn = myTurn;
    }
}
