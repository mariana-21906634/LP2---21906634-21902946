package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GameManager {

    ArrayList<Programmer> programmers = new ArrayList<>();
    ArrayList<Integer> jogada = new ArrayList<>();
    int tamanhoTab, nrTurnos;
    Programmer winner;

    public GameManager(){

    }

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

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools){
        return false;
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

    public String getTitle(int position){
        return null;
    }

    public List<Programmer> getProgrammers(boolean includeDefeated){
        return programmers;
    }

    public List<Programmer> getProgrammers(int position){
        ArrayList<Programmer> programadores = new ArrayList<>();
            for(Programmer programmer:programmers){
                if(programmer.getPos()==position){
                    programadores.add(programmer);
                }
            }
            return programadores;
    }

    public String getProgrammersInfo(){
        return null;
    }

    public int getCurrentPlayerID(){
        return jogada.get(0);
    }

    public boolean moveCurrentPlayer(int nrPositions){
        if(nrPositions < 1 || nrPositions > 6){
            return false;
        }
        for(Programmer programmer : programmers) {
            if(programmer.getId()==jogada.get(0)) {
                int posAtualPlayer = programmer.getPos();
                if (posAtualPlayer + nrPositions > tamanhoTab) {
                    int excess = tamanhoTab - posAtualPlayer - nrPositions;
                    programmer.setPos(tamanhoTab - Math.abs(excess));
                } else {
                    programmer.setPos(posAtualPlayer + nrPositions);
                }
            }
        }
        nrTurnos++;
        jogada.add(jogada.get(0));
        jogada.remove(0);
        return true;
    }

    public String reactToAbyssOrTool(){
        return null;
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

    public List<String> getGameResults(){
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
