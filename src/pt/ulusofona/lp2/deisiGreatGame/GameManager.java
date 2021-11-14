package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;
import java.util.ArrayList;

public class GameManager {

    ArrayList<Programmer> programmers = new ArrayList<>();
    int tamanhoTab;

    public GameManager(){}

    public boolean createInitialBoard(String[][] playerInfo, int boardSize){
        return false;
    }

    public String getImagePng(int position){
        if(position==tamanhoTab){
            return "glory.png";
        }
        if(position > tamanhoTab){
            return null;
        }return "blank.png";
    }

    public ArrayList<Programmer> getProgrammers(){
        return programmers;
    } //FEITO

    public ArrayList<Programmer> getProgrammers(int position){
        ArrayList<Programmer> programadores = new ArrayList<>();
        if(position > 0 && position < tamanhoTab){
            for(Programmer programmer:programmers){
                if(programmer.getPos()==position){
                    programadores.add(programmer);
                }
            }
            return programadores;
        }else{
            return null;
        }
    }  // ACHO QUE FEITO

    public int getCurrentPlayerID(){
        return 0;
    }

    public boolean moveCurrentPlayer(int nrPositions){
        if(nrPositions < 1 || nrPositions > 6){
            return false;
        }
        return true;
    }

    public boolean gameIsOver(){
        return false;
    }

    public ArrayList<String> getGameResults(){
        return null;
    }

    public JPanel getAuthorsPanel(){
        return null;
    } //FEITO
}
