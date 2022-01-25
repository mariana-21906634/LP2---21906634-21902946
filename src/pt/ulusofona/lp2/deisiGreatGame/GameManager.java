package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;
import java.util.*;

public class GameManager {

    HashMap<Integer, AbyssOrTool> abismoFerramentas = new HashMap();
    HashMap<Integer, Programmer> programmers = new HashMap<>();
    HashMap<Integer, ArrayList<Integer>> tools = new HashMap<>();
    HashMap<Integer, Integer> posID = new HashMap<>();
    ArrayList<Integer> jogada = new ArrayList<>();
    int tamanhoTab, nrTurnos, dados;
    Programmer winner;


    public GameManager(){

    }

    public void createInitialBoard(String[][] playerInfo, int boardSize) throws InvalidInitialBoardException{
        reset();
        if(playerInfo.length < 2 || playerInfo.length > 4 || boardSize < playerInfo.length*2){
            throw new InvalidInitialBoardException();
        }
        tamanhoTab = boardSize;
        for (String[] player : playerInfo) {
            programmers.put(Integer.parseInt(player[0]), new Programmer(Integer.parseInt(player[0]), player[1], player[2], player[3]));
            jogada.add(Integer.parseInt(player[0]));
        }
        Collections.sort(jogada);
    }  //FEITO

    public void createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) throws InvalidInitialBoardException{

        abismoFerramentas.clear();
        tools.clear();
        tools.put(0, new ArrayList<>());
        tools.put(1, new ArrayList<>());
        tools.put(2, new ArrayList<>());
        tools.put(3, new ArrayList<>());
        tools.put(4, new ArrayList<>());
        tools.put(5, new ArrayList<>());
        if(abyssesAndTools != null){
            int x = 0;
            for (String[] aux : abyssesAndTools) {
                //validar sub type id lido
                if (Integer.parseInt(aux[0]) == 0 && (Integer.parseInt(aux[1])<0 || Integer.parseInt(aux[1])>9)){
                    throw new InvalidInitialBoardException();
                }
                if (Integer.parseInt(aux[0]) == 1 && (Integer.parseInt(aux[1])<0 || Integer.parseInt(aux[1])>5)){
                    throw new InvalidInitialBoardException();
                }
                if (Integer.parseInt(aux[0]) > 1 || Integer.parseInt(aux[0]) < 0 || Integer.parseInt(aux[2]) > worldSize) {
                    throw new InvalidInitialBoardException();
                }
                AbyssOrTool objeto = new AbyssOrTool(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]), Integer.parseInt(aux[2]));
                abismoFerramentas.put(x, objeto);
                x++;
            }
        }
        createInitialBoard(playerInfo, worldSize);
    }  //FEITO

    public String getImagePng(int position){
        if(position==tamanhoTab){
            return "glory.png";
        }
        if(position > tamanhoTab){
            return null;
        }
        for (int i = 1; i<= programmers.size(); i++){
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
        List<Programmer> undefeated = new ArrayList<>();
        if (includeDefeated) {
            ArrayList<Programmer> programa = new ArrayList<>(programmers.values());
            return programa;
        }
        for (Map.Entry<Integer, Programmer> jogo : programmers.entrySet()) {
            if (jogo.getValue().getEstado().equals("Em Jogo")) {
                undefeated.add(jogo.getValue());
            }
        }
        return undefeated;
    }  //FEITO

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
        StringBuilder informacao = new StringBuilder();
        for(Map.Entry<Integer, Programmer> programmer : programmers.entrySet()){
            if(informacao.length() == 0){
                informacao.append(programmer.getValue().getName()).append(" : ");
            }else{
                informacao.append(" | ").append(programmer.getValue().getName()).append(" : ");
            }
            if(programmer.getValue().getFerramentas().isEmpty()){
                informacao.append("No tools");
            }else{
                for(Map.Entry<Integer, AbyssOrTool> ferramentas : programmer.getValue().getFerramentas().entrySet()){
                    informacao.append(ferramentas.getValue().getNome()).append(";");
                }
                informacao.deleteCharAt(informacao.length()-1);
            }
        }
        return informacao.toString();
    }

    public int getCurrentPlayerID(){
        return jogada.get(0);
    }  //FEITO

    public boolean moveCurrentPlayer(int nrSpaces){
        int momentoAtual = programmers.get(jogada.get(0)).getPos();
        int aux;
        if(nrSpaces < 1 || nrSpaces > 6){
            return false;
        }
        if(tamanhoTab < momentoAtual + nrSpaces){
            aux = tamanhoTab-momentoAtual-nrSpaces;
            programmers.get(jogada.get(0)).setPos(tamanhoTab-Math.abs(aux));
        }else{
            programmers.get(jogada.get(0)).setPos(momentoAtual+nrSpaces);
        }
        posID.put(jogada.get(0), programmers.get(jogada.get(0)).getPos());
        return true;
    }

    public String reactToAbyssOrTool() {
        int posAtual = programmers.get(jogada.get(0)).getPos();
        String explicacao = null;
        if (getTitle(posAtual) != null) {
            for (Map.Entry<Integer, AbyssOrTool> a : abismoFerramentas.entrySet()) {
                if (a.getValue().getPosicao() == posAtual) {
                    if (a.getValue().getId() == 0) {


                        switch (a.getValue().getIdTipo()) {
                            case 0 -> {
                                if (!tools.get(4).contains(jogada.get(0)) && !tools.get(5).contains(jogada.get(0))) {
                                    programmers.get(jogada.get(0)).setPos(posAtual - 1);
                                    explicacao = "O programador recua 1 casa.";
                                } else {
                                    explicacao = "O abismo não tem efeito sobre ti.";
                                    if (tools.get(4).contains(jogada.get(0))) {
                                        tools.get(4).remove(jogada.get(0));
                                        programmers.get(jogada.get(0)).removeFerramenta(4);
                                    } else {
                                        tools.get(5).remove(jogada.get(0));
                                        programmers.get(jogada.get(0)).removeFerramenta(5);
                                    }
                                }
                            } // erro de sintaxe - feito
                            case 1 -> {
                                if (!tools.get(2).contains(tools.get(0)) || !tools.get(5).contains(jogada.get(0))) {
                                    programmers.get(jogada.get(0)).setPos(posAtual - (dados / 2));
                                    int dado = dados/2;
                                    explicacao = "Recua " + dado + " casa(s)";
                                } else {
                                    if (tools.get(2).contains(jogada.get(0))) {
                                        tools.get(2).remove(jogada.get(0));
                                        programmers.get(jogada.get(0)).removeFerramenta(2);
                                    } else {
                                        tools.get(5).remove(jogada.get(0));
                                        programmers.get(jogada.get(0)).removeFerramenta(5);
                                    }
                                }
                            }  // erro de logica - feito
                            case 2 -> {
                                if (!tools.get(3).contains(jogada.get(0)) && !tools.get(5).contains(jogada.get(0))) {
                                    programmers.get(jogada.get(0)).setPos(posAtual - 2);
                                    explicacao = "O programador recua 2 casas.";
                                } else {
                                    explicacao = "O abismo não tem efeito sobre ti.";
                                    if (tools.get(3).contains(jogada.get(0))) {
                                        tools.get(3).remove(jogada.get(0));
                                        programmers.get(jogada.get(0)).removeFerramenta(3);
                                    } else {
                                        tools.get(5).remove(jogada.get(0));
                                        programmers.get(jogada.get(0)).removeFerramenta(5);
                                    }
                                }
                            } // exception - feito
                            case 3 -> {
                                if (!tools.get(3).contains(jogada.get(0)) && !tools.get(5).contains(jogada.get(0))) {
                                    programmers.get(jogada.get(0)).setPos(posAtual - 3);
                                    explicacao = "O programador recua 3 casas.";
                                } else {
                                    explicacao = "O abismo não tem efeito sobre ti.";
                                    if (tools.get(3).contains(jogada.get(0))) {
                                        tools.get(3).remove(jogada.get(0));
                                        programmers.get(jogada.get(0)).removeFerramenta(3);
                                    } else {
                                        tools.get(5).remove(jogada.get(0));
                                        programmers.get(jogada.get(0)).removeFerramenta(5);
                                    }
                                }
                            } // file not found exception - feito
                            case 4 -> {
                                programmers.get(jogada.get(0)).setPos(1);
                                explicacao = "O programador volta à primeira casa do jogo.";
                            } // Crash (aka Rebentanço) - feito
                            case 5 -> {
                                if (!tools.get(0).contains(jogada.get(0))) {
                                    programmers.get(jogada.get(0)).setPos(posAtual - dados);
                                    explicacao = "O programador recua até à casa onde estava antes de chegar a esta casa.";
                                } else {
                                    explicacao = "O abismo não tem efeito sobre ti.";
                                    tools.get(0).remove(jogada.get(0));
                                    programmers.get(jogada.get(0)).removeFerramenta(0);
                                }
                            } // duplicated code abismo - feito

                            case 6 -> {
                                if (!tools.get(1).contains(jogada.get(0))) {
                                    programmers.get(jogada.get(0)).setPos(posAtual); // jogada default
                                    explicacao = "O programador recua para a posição onde estava há 2 movimentos atrás.";
                                } else {
                                    explicacao = "O abismo não tem efeito sobre ti.";
                                    tools.get(1).remove(jogada.get(0));
                                    programmers.get(jogada.get(0)).removeFerramenta(1);
                                }
                            } // efeitos secundarios abismo - por fazer

                            case 7 -> {
                                programmers.get(jogada.get(0)).setEstado("Derrotado");
                                posID.put(jogada.get(0), programmers.get(jogada.get(0)).getPos());
                                explicacao = "O programador perde imediatamente o jogo.";
                                jogada.remove(0);
                                nrTurnos++;
                                return explicacao;
                            } // BSOD - feito

                            case 8 -> {
                                if (!tools.get(1).contains(jogada.get(0))) {
                                    programmers.get(jogada.get(0)).setPos(posAtual); // jogada default
                                    explicacao = "O programador fica preso na casa onde está até que lá apareça outro programador para o ajuda";
                                } else {
                                    explicacao = "O abismo não tem efeito sobre ti.";
                                    tools.get(1).remove(jogada.get(0));
                                    programmers.get(jogada.get(0)).removeFerramenta(1);
                                }
                            } // ciclo infinito abismo - por fazer

                            case 9 -> {
                                int i = 0;
                                ArrayList<Integer> iDs = new ArrayList<>();
                                for (Map.Entry<Integer, Programmer> j : programmers.entrySet()) {
                                    if (a.getValue().getPosicao() == j.getValue().getPos()) {
                                        iDs.add(j.getValue().getId());
                                        i++;
                                    }
                                }
                                if (i >= 2) {
                                    for (int id : iDs) {
                                        programmers.get(id).setPos(posAtual - 3);
                                    }
                                    explicacao = "Todos os jogadores nessa casa recuam 3 casas";
                                }else{
                                    explicacao = "Se outro jogador cair nesta casa, recuam os dois 3 casas.";
                                }
                            } // Segmentation fault - feito
                        }


                    } else {
                        if (!tools.get(a.getValue().getIdTipo()).contains(jogada.get(0))) {
                            programmers.get(jogada.get(0)).setFerramentas(a.getValue());
                            tools.get(a.getValue().getIdTipo()).add(jogada.get(0));
                            switch (a.getValue().getIdTipo()) {
                                case 0 -> explicacao = "Herança - evita os efeitos do abismo (Duplicated code)";
                                case 1 -> explicacao = "Programação funcional - evita os efeitos dos abismos (Efeitos secundários e Ciclo infinito)";
                                case 2 -> explicacao = "Testes unitários - evita os efeitos do abismo (Erro de lógica)";
                                case 3 -> explicacao = "Tratamento de excepções - evita os efeitos dos abismos (Exception e File not found exception)";
                                case 4 -> explicacao = "IDE - evita os efeitos do abismo (Erro de sintaxe)";
                                case 5 -> explicacao = "Ajuda do professor - tem o mesmo efeito que as seguintes ferramentas (Testes unitários, IDE e Tratamento de excepções)";
                            }
                        } else {
                            explicacao = "Já existe uma ferramenta igual a esta";
                        }
                    }
                }
            }
        }
        posID.put(jogada.get(0), programmers.get(jogada.get(0)).getPos());
        jogada.add(jogada.remove(0));
        nrTurnos++;
        return explicacao;
    }

    public boolean gameIsOver(){
        int i = 0;
        for(Map.Entry<Integer, Integer> programa : posID.entrySet()){
            if(programa.getValue()==tamanhoTab){
                winner = programmers.get(programa.getKey());
                return true;
            }
        }
        for (Map.Entry<Integer, Programmer> jogo : programmers.entrySet()) {
            if (jogo.getValue().getEstado().equals("Em Jogo")) {
                i++;
            }
        }
        return i == 1;
    }  //FEITO

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
