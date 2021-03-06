package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Programmer {

    int id, pos;
    String name, linguagemFavorita, estado, color;
    HashMap<Integer,AbyssOrTool> ferramentas = new HashMap<>();
    ArrayList<Integer> posicoes = new ArrayList<>();


    public Programmer(){}

    public Programmer(int id, String name, String linguagemFavorita, String color) {
        this.id = id;
        this.name = name;
        this.linguagemFavorita = linguagemFavorita;
        lingCorrecao();
        this.color = color;
        this.pos = 1;
        this.estado ="Em Jogo";
    }

    public void lingCorrecao(){
        String[] linguagens = this.linguagemFavorita.split(";");
        if (linguagens.length > 1) {
            Arrays.sort(linguagens);
            this.linguagemFavorita = String.join("; ", linguagens);
        }
    }

    public int getId() {
        return this.id;
    }

    public String getEstado() {
        return estado;
    }

    public HashMap<Integer, AbyssOrTool> getFerramentas() {
        return ferramentas;
    }

    public void setFerramentas(AbyssOrTool ferramentas) {
        this.ferramentas.put(ferramentas.getIdTipo(),ferramentas);
    }

    public String getLinguagemFavorita() {
        return linguagemFavorita;
    }

    public void removeFerramenta(Integer id){
        this.ferramentas.remove(id);
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getName() {
        return this.name;
    }

    public int getPos() {
        return this.pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public ProgrammerColor getColor() {
        return switch (color.toUpperCase()) {
            case "PURPLE" -> ProgrammerColor.PURPLE;
            case "BLUE" -> ProgrammerColor.BLUE;
            case "BROWN" -> ProgrammerColor.BROWN;
            case "GREEN" -> ProgrammerColor.GREEN;
            default -> null;
        };
    }

    public ArrayList getPosicoes(){
        return posicoes;
    }

    public void setPosicoes(int pos){
        posicoes.add(pos);
    }



    @Override
    public String toString() {
        if (this.ferramentas.size() == 0){
            return this.id + " | " + this.name + " | " + this.pos + " | No tools | " + this.linguagemFavorita + " | " + this.estado;
        }
        String muitasFerramentas = "";
        System.out.println("DB: "+ferramentas.values().size());  //teste
        for(AbyssOrTool at: this.ferramentas.values()){
            muitasFerramentas+= at.getNome()+" | ";
        }
        return this.id + " | " + this.name + " | " + this.pos + " | " + muitasFerramentas  + this.linguagemFavorita + " | " + this.estado;
    }
}