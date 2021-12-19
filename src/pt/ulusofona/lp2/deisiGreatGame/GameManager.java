package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;
import java.util.*;

public class GameManager {

    HashMap<Integer, AbyssOrTool> abismoFerramentas = new HashMap();
    HashMap<Integer, Programmer> programmers = new HashMap<>();
    HashMap<Integer, ArrayList<Integer>> tools = new HashMap<>();
    HashMap<Integer, Integer> posID = new HashMap<>();
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
            programmers.put(Integer.parseInt(player[0]), new Programmer(Integer.parseInt(player[0]), player[1], player[2], player[3]));
            jogada.add(Integer.parseInt(player[0]));
        }
        Collections.sort(jogada);
        return true;
    }  //FEITO

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools){
        abismoFerramentas.clear();
        tools.put(0, new ArrayList<>());
        tools.put(1, new ArrayList<>());
        tools.put(2, new ArrayList<>());
        tools.put(3, new ArrayList<>());
        tools.put(4, new ArrayList<>());
        tools.put(5, new ArrayList<>());
        if(abyssesAndTools != null){
            int x = 0;
            for (String[] aux : abyssesAndTools) {
                if (Integer.parseInt(aux[0]) > 1 || Integer.parseInt(aux[0]) < 0 || Integer.parseInt(aux[2]) > worldSize) {
                    return false;
                }
                AbyssOrTool objeto = new AbyssOrTool(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]), Integer.parseInt(aux[2]));
                abismoFerramentas.put(x, objeto);
                x++;
            }
        }
        createInitialBoard(playerInfo, worldSize);
        return true;
    }  //FEITO

    public String getImagePng(int position){
        if(position==tamanhoTab){
            return "glory.png";
        }
        if(position > tamanhoTab){
            return null;
        }
        for (int i = 0; i< programmers.size(); i++){     //se aqui muda ou nao
            if (programmers.get(i).getPos() == position){
                return switch (programmers.get(i).getColor()){
                    case PURPLE -> "playerPurple.png";
                    case BLUE -> "playerBlue.png";
                    case GREEN -> "playerGreen.png";
                    case BROWN -> "playerBrown.png";
                };
            }

        }
        for (Map.Entry<Integer, AbyssOrTool> abismoFerramenta : abismoFerramentas.entrySet()) {
            if (abismoFerramenta.getValue().getPosicao() == position) {
                return abismoFerramenta.getValue().images();
            }
        }
        return "blank.png";
    }  //FEITO

    public String getTitle(int position){
        for (Map.Entry<Integer, AbyssOrTool> abismoFerramenta : abismoFerramentas.entrySet()) {
            if (abismoFerramenta.getValue().getPosicao() == position) {
                return abismoFerramenta.getValue().getNome();
            }
        }
        return null;
    }  //FEITO

    public List<Programmer> getProgrammers(boolean includeDefeated){
        /*List<Programmer> undefeated = new ArrayList<>();
        if (includeDefeated) {
            return new ArrayList<>(jogadores.values());
        }
        for (Map.Entry<Integer, Programmer> j : jogadores.entrySet()) {
            if (j.getValue().getEstado().equals("Em Jogo")) {
                undefeated.add(j.getValue());
            }
        }
        return undefeated;
        */
        return null;
    }

    public List<Programmer> getProgrammers(int position){
        ArrayList<Programmer> programadores = new ArrayList<>();
            for (Map.Entry<Integer, Integer> programmer : posID.entrySet()) {
                if(programmer.getValue()==position){
                    programadores.add(programmers.get(programmer.getKey()));
                }
            }
            return programadores;
    }  //FEITO

    public String getProgrammersInfo(){
        return null;
    }

    public int getCurrentPlayerID(){
        return jogada.get(0);
    }  //FEITO

    public boolean moveCurrentPlayer(int nrSpaces){

        return true;
    }

    public String reactToAbyssOrTool(){
        return null;
    }

    public boolean gameIsOver(){
        for(Map.Entry<Integer, Integer> p : posID.entrySet()){
            if(p.getValue()==tamanhoTab){
                winner = programmers.get(p.getKey());
                return true;
            }
        }
        return false;
    }

    public List<String> getGameResults(){
        ArrayList<String> gameResults = new ArrayList<>();
        ArrayList<Integer> posicao = new ArrayList<>();
        for (Map.Entry<Integer, Integer> programadores : posID.entrySet()) {
            posicao.add(programadores.getValue());
        }
        gameResults.add("O GRANDE JOGO DO DEISI");
        gameResults.add("");
        gameResults.add("NR. DE TURNOS");
        gameResults.add(Integer.toString(nrTurnos));
        gameResults.add("");
        gameResults.add("VENCEDOR");
        gameResults.add(winner.getName());
        gameResults.add("");
        gameResults.add("RESTANTES");
        Collections.sort(posicao);
        Collections.reverse(posicao);
        for (Integer pos : posicao) {
            for (Map.Entry<Integer, Programmer> prog : programmers.entrySet()) {
                if (prog.getValue().getPos() == pos && !prog.getValue().getName().equals(winner.getName())) {
                    if (!gameResults.contains(prog.getValue().getName() + " " + pos)) {
                        gameResults.add(prog.getValue().getName() + " " + pos);
                    }
                }
            }
        }
        return gameResults;
    }  //FEITO

    public JPanel getAuthorsPanel(){
        return null;
    } //FEITO

    public void reset() {
        programmers.clear();
        jogada.clear();
        posID.clear();
        nrTurnos = 1;
    }  //FEITO
}
