package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.Serializable;

public class Move implements Serializable {
    int Row;
    int Col;
    String Turn;
    public Move(int row, int col, String turn){
        this.Row = row;
        this.Col = col;
        this.Turn = turn;
    }
    public int getRow(){
        return Row;
    }
    public int getCol(){
        return Col;
    }
    public String getTurn(){
        return Turn;
    }
}
