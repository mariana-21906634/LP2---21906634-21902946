package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;
import java.util.ArrayList;

public class GameManager {

    ArrayList<Programmer> programmers;

    public GameManager(){}

    public boolean createInitialBoard(String[][] playerInfo, int boardSize){
        return false;
    }

    public String getImagePng(int position){
        return "";
    }

    public ArrayList<Programmer> getProgrammers(){
        return programmers;
    }

    public ArrayList<Programmer> getProgrammers(int position){
        return null;
    }

    public int getCurrentPlayerID(){
        return 0;
    }

    public boolean moveCurrentPlayer(int nrPositions){
        return false;
    }

    public boolean gameIsOver(){
        return false;
    }

    public ArrayList<String> getGameResults(){
        return null;
    }

    public JPanel getAuthorsPanel(){
        return null;
    }
}
