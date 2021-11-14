package pt.ulusofona.lp2.deisiGreatGame;

public class Programmer {

    int id, pos;
    String name, linguagemFavorita, estado, color;

    public Programmer(){}

    public Programmer(int id, String name, String linguagemFavorita, String color) {
        this.id = id;
        this.name = name;
        this.linguagemFavorita = linguagemFavorita;
        this.color = color;
        this.pos = pos;
        this.estado = "Em Jogo";
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



    public ProgrammerColor getColor() {
        return switch (color.toLowerCase()) {
            case "purple" -> ProgrammerColor.PURPLE;
            case "blue" -> ProgrammerColor.BLUE;
            case "brown" -> ProgrammerColor.BROWN;
            case "green" -> ProgrammerColor.GREEN;
            default -> null;
        };
    }



    @Override
    public String toString() {
        return this.id + " | " + this.name + " | " + this.pos + " | " + this.linguagemFavorita + " | " + this.estado;
    }
}
