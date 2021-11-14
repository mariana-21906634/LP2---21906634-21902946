package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GameManager {

    ArrayList<Programmer> programmers = new ArrayList<>();
    ArrayList<Integer> jogada = new ArrayList<>();
    int tamanhoTab, nrTurnos;

    public GameManager(){}

    public boolean createInitialBoard(String[][] playerInfo, int boardSize){
        reset();
        if(playerInfo.length < 2 || playerInfo.length > 4 || boardSize < playerInfo.length*2){
            return false;
        }
        tamanhoTab = boardSize;
        for (String[] player : playerInfo) {
            programmers.add(new Programmer(Integer.parseInt(player[0]), player[1], player[2], player[3]));
            jogada.add(Integer.parseInt(player[0]));
        }
        Collections.sort(jogada);
        return true;
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

    public void reset() {
        programmers.clear();
        jogada.clear();
        nrTurnos = 1;
    }
}
