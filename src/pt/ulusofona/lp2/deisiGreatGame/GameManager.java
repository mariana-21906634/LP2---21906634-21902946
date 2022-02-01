package pt.ulusofona.lp2.deisiGreatGame;

import javax.naming.InterruptedNamingException;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
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
        if(playerInfo.length < 2 || playerInfo.length > 4){
            throw new InvalidInitialBoardException("O número de jogadores é inválido");
        }
        if (boardSize < playerInfo.length*2){
            throw new InvalidInitialBoardException("O tamanho do tabuleiro é inválido");
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
            for (String[] aux : abyssesAndTools) {  // aux = abismo ou ferramenta
                //validar sub type id lido
                if (Integer.parseInt(aux[0]) == 0 && (Integer.parseInt(aux[1])<0 || Integer.parseInt(aux[1])>10)){
                    throw new InvalidInitialBoardException("O abismo é inválido", aux[1], true, false );
                }
                if (Integer.parseInt(aux[0]) == 1 && (Integer.parseInt(aux[1])<0 || Integer.parseInt(aux[1])>5)){
                    throw new InvalidInitialBoardException("A ferramenta é inválida", aux[1], false, true);
                }
                if (Integer.parseInt(aux[0]) > 1 || Integer.parseInt(aux[0]) < 0 || Integer.parseInt(aux[2]) > worldSize) {
                    throw new InvalidInitialBoardException("O abismo ou uma ferramenta está inválida", aux[1], false, false);
                }
                AbyssOrTool objeto = new AbyssOrTool(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]), Integer.parseInt(aux[2]));
                abismoFerramentas.put(x, objeto);
                x++;
            }
        }
        createInitialBoard(playerInfo, worldSize);
    }  //FEITO

    public boolean loadGame(File file) {
        if(file == null){
            return false;
        }
        reset();
        abismoFerramentas.clear();
        tools.clear();
        tools.put(0, new ArrayList<>());
        tools.put(1, new ArrayList<>());
        tools.put(2, new ArrayList<>());
        tools.put(3, new ArrayList<>());
        tools.put(4, new ArrayList<>());
        tools.put(5, new ArrayList<>());
        try {
            Scanner ler = new Scanner(file);
            String linha = "";
            String[] divideLinha;
            int count = 0;
            while (ler.hasNextLine()){
                linha = ler.nextLine();
                divideLinha = linha.split(" : ");
                if (divideLinha[0].contains("f")){
                    for (int i = 1; i < divideLinha.length; i++){
                        tools.get(Integer.parseInt(divideLinha[0].substring(0,
                                divideLinha[0].length()-1))).add(Integer.parseInt(divideLinha[i]));
                    }
                }else{
                    switch (divideLinha.length){
                        case 1 -> {
                          switch (count){
                              case 0 -> {
                                  tamanhoTab = Integer.parseInt(divideLinha[0]);
                                  count++;
                              }
                              case 1 -> {
                                  nrTurnos = Integer.parseInt(divideLinha[0]);
                                  count++;
                              }
                              case 2 -> {
                                  dados = Integer.parseInt(divideLinha[0]);
                                  count++;
                              }
                              case 3 -> jogada.add(Integer.parseInt(divideLinha[0]));
                          }
                        }
                        case 2 -> posID.put(Integer.parseInt(divideLinha[0]), Integer.parseInt(divideLinha[1]));
                        case 3 -> abismoFerramentas.put(abismoFerramentas.size(),
                                new AbyssOrTool(Integer.parseInt(divideLinha[0]), Integer.parseInt(divideLinha[1]),
                                        Integer.parseInt(divideLinha[2])));
                        case 7 -> {
                            Programmer progamador = new Programmer(Integer.parseInt(divideLinha[0]),
                                    divideLinha[1], divideLinha[2], divideLinha[3]);
                            progamador.setPos(Integer.parseInt(divideLinha[4]));
                            progamador.setEstado(divideLinha[5]);
                            if (!divideLinha[6].contains("Nenhuma Ferramenta")){
                                String[] ferramenta = divideLinha[6].split(" - ");
                                if (ferramenta.length > 3){
                                    for (int i = 5; i < ferramenta.length; i+= 3){
                                        progamador.setFerramentas(new AbyssOrTool(Integer.parseInt(ferramenta[i-2])
                                                , Integer.parseInt(ferramenta[i-1]),
                                                Integer.parseInt(ferramenta[i])));
                                    }
                                }else{
                                    progamador.setFerramentas(new AbyssOrTool(Integer.parseInt(ferramenta[0])
                                    , Integer.parseInt(ferramenta[1]), Integer.parseInt(ferramenta[2])));
                                }
                            }
                            programmers.put(Integer.parseInt(divideLinha[0]), progamador);
                        }
                    }
                }
            }
            ler.close();
        }catch (FileNotFoundException e){
            return false;
        }
        return true;
    }

    public boolean saveGame(File file) {
        try{
            if(file != null && file.createNewFile()){
                FileWriter save = new FileWriter(file.getName());
                save.write(tamanhoTab + "\n" + nrTurnos + "\n" + dados + "\n");
                for(Map.Entry<Integer, AbyssOrTool> abs : abismoFerramentas.entrySet()){
                    if(abs != null){
                        save.write(abs.getValue().id + " : " + abs.getValue().idTipo + " : " + abs.getValue().posicao + "\n");
                    }
                }
                for(Map.Entry<Integer, Programmer> prog : programmers.entrySet()){
                    if(prog != null){
                        if(prog.getValue().getFerramentas().isEmpty()){
                            save.write(prog.getValue().getId() + " : " + prog.getValue().getName() + " : " +
                                    prog.getValue().getLinguagemFavorita() + " : " + prog.getValue().getColor() + " : "
                            + prog.getValue().getPos() + " : " + prog.getValue().getEstado() + " : Nenhuma Ferramenta"
                                    + "\n");
                        }else{
                            StringBuilder ferramentas = new StringBuilder();
                            prog.getValue().getFerramentas().values().forEach(v -> ferramentas.append(v.getId()).         //vai criar um string com as ferramentas todas
                                    append(" - ").append(v.getIdTipo()).append(" - ").append(v.getPosicao()).append(" - "));
                            save.write(prog.getValue().getId() + " : " + prog.getValue().getName() + " : " +
                                    prog.getValue().getLinguagemFavorita() + " : " + prog.getValue().getColor() + " : "
                                    + prog.getValue().getPos() + " : " + prog.getValue().getEstado() + " : " +
                                    ferramentas.substring(0, ferramentas.length()-3) + "\n");
                        }
                    }
                }
                for(Map.Entry<Integer, ArrayList<Integer>> tool : tools.entrySet()){
                    if(tool != null){
                        StringBuilder ferramentas = new StringBuilder();
                        ferramentas.append(tool.getKey()).append("f");
                        for(int f : tool.getValue()){
                            ferramentas.append(" : ").append(f);
                        }
                        save.write(ferramentas + "\n");
                    }
                }
                for(Map.Entry<Integer, Integer> id : posID.entrySet()){
                    if(id != null){
                        save.write(id.getKey() + " : " + id.getValue() + "\n");
                    }
                }
                for(Integer jogada : jogada){
                    save.write(jogada + "\n");
                }
               save.close();
            }else{
                return false;
            }
        }catch (IOException e){
            return false;
        }
        return true;
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
        int posicaoAtual = programmers.get(jogada.get(0)).getPos();
        int keyAbismoFerramenta = 0;
        String explicacao = null;
        if (getTitle(posicaoAtual) != null) {

            for (Map.Entry<Integer, AbyssOrTool> abismoFerramenta : abismoFerramentas.entrySet()) {                    //guardar a key do abismo ou ferramenta onde o player ta
                if (abismoFerramenta.getValue().getPosicao() == programmers.get(jogada.get(0)).getPos()) {
                    keyAbismoFerramenta = abismoFerramenta.getKey();
                }
            }


                if (abismoFerramentas.get(keyAbismoFerramenta).getId() == 0) {
                    switch (abismoFerramentas.get(keyAbismoFerramenta).getIdTipo()) {
                        case 0 -> {
                            if (!tools.get(4).contains(jogada.get(0)) && !tools.get(5).contains(jogada.get(0))) {
                                programmers.get(jogada.get(0)).setPos(posicaoAtual - 1);
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
                            if (!tools.get(2).contains(jogada.get(0)) || !tools.get(5).contains(jogada.get(0))) {
                                double dado = (double) dados / 2;
                                programmers.get(jogada.get(0)).setPos(posicaoAtual - (int) Math.floor(dado));
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
                                programmers.get(jogada.get(0)).setPos(posicaoAtual - 2);
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
                                programmers.get(jogada.get(0)).setPos(posicaoAtual - 3);
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
                                programmers.get(jogada.get(0)).setPos(posicaoAtual - dados);
                                explicacao = "O programador recua até à casa onde estava antes de chegar a esta casa.";
                            } else {
                                explicacao = "O abismo não tem efeito sobre ti.";
                                tools.get(0).remove(jogada.get(0));
                                programmers.get(jogada.get(0)).removeFerramenta(0);
                            }
                        } // duplicated code abismo - feito

                        case 6 -> {
                            if (!tools.get(1).contains(jogada.get(0))) {
                                programmers.get(jogada.get(0)).setPos(posicaoAtual); // jogada default
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
                                programmers.get(jogada.get(0)).setPos(posicaoAtual); // jogada default
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
                                if (abismoFerramentas.get(keyAbismoFerramenta).getPosicao() == j.getValue().getPos()) {
                                    iDs.add(j.getValue().getId());
                                    i++;
                                }
                            }
                            if (i >= 2) {
                                for (int id : iDs) {
                                    programmers.get(id).setPos(posicaoAtual - 3);
                                }
                                explicacao = "Todos os jogadores nessa casa recuam 3 casas";
                            } else {
                                explicacao = "Se outro jogador cair nesta casa, recuam os dois 3 casas.";
                            }
                        } // Segmentation fault - feito

                        case 10 ->{
                            int media = 0;

                        }
                    }


                } else {
                    if (!tools.get(abismoFerramentas.get(keyAbismoFerramenta).getIdTipo()).contains(jogada.get(0))) {
                        programmers.get(jogada.get(0)).setFerramentas(abismoFerramentas.get(keyAbismoFerramenta));
                        tools.get(abismoFerramentas.get(keyAbismoFerramenta).getIdTipo()).add(jogada.get(0));
                        switch (abismoFerramentas.get(keyAbismoFerramenta).getIdTipo()) {
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
