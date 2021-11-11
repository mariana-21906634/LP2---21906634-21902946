package pt.ulusofona.lp2.deisiGreatGame;

public class Programmer {

    int id, pos;
    ProgrammerColor color;
    String name, linguagemFavorita, estado;

    public Programmer(){}

    public Programmer(int id, String name, ProgrammerColor color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Programmer(int id, int pos, ProgrammerColor color, String name, String linguagemFavorita, String estado) {
        this.id = id;
        this.pos = pos;
        this.color = color;
        this.name = name;
        this.linguagemFavorita = linguagemFavorita;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProgrammerColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + pos + " | " + linguagemFavorita + " | " + estado;
    }
}
