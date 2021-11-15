package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GameManager {

    ArrayList<Programmer> programmers = new ArrayList<>();
    ArrayList<Integer> jogada = new ArrayList<>();
    int tamanhoTab, nrTurnos;
    Programmer winner;

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
        }
        for (int i = 0; i< programmers.size(); i++){
            if (programmers.get(i).getPos() == position){
                return switch (programmers.get(i).getColor()){
                    case PURPLE -> "playerPurple.png";
                    case BLUE -> "playerBlue.png";
                    case GREEN -> "playerGreen.png";
                    case BROWN -> "playerBrown.png";
                };
            }

        }
        return "blank.png";
    }

    public ArrayList<Programmer> getProgrammers(){
        return programmers;
    }

    public ArrayList<Programmer> getProgrammers(int position){
        ArrayList<Programmer> programadores = new ArrayList<>();
            for(Programmer programmer:programmers){
                if(programmer.getPos()==position){
                    programadores.add(programmer);
                }
            }
            return programadores;
    }

    public int getCurrentPlayerID(){
        return jogada.get(0);
    }

    public boolean moveCurrentPlayer(int nrPositions){
        if(nrPositions < 1 || nrPositions > 6){
            return false;
        }
        return true;
    }

    public boolean gameIsOver(){
        for(Programmer programmer:programmers){
            if(programmer.getPos()==tamanhoTab){
                winner = programmer;
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getGameResults(){
        ArrayList<String> gameResults = new ArrayList<>();
        gameResults.add("O GRANDE JOGO DO DEISI");
        gameResults.add("");
        gameResults.add("NR. DE TURNOS");
        gameResults.add(Integer.toString(nrTurnos));
        gameResults.add("");
        gameResults.add("VENCEDOR");
        gameResults.add(winner.getName());
        gameResults.add("");
        gameResults.add("RESTANTES");
        Collections.sort(programmers, new Sortbypos());
        for(Programmer programmer:programmers){
            if (!programmer.getName().equals(winner.getName())) {
                if (!gameResults.contains(programmer.getName() + " " + programmer.getPos())) {
                    gameResults.add(programmer.getName() + " " + programmer.getPos());
                }
            }
        }
        return gameResults;
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
