package pt.ulusofona.lp2.deisiGreatGame;

import java.util.Arrays;

public class Programmer {

    int id, pos;
    String name, linguagemFavorita, estado, color;

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



    @Override
    public String toString() {
        return this.id + " | " + this.name + " | " + this.pos + " | " + this.linguagemFavorita + " | " + this.estado;
    }
}