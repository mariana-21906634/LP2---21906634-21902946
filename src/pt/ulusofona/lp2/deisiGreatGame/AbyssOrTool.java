package pt.ulusofona.lp2.deisiGreatGame;

public class AbyssOrTool {
    int id, posicao, idTipo;

    AbyssOrTool(int id,int idTipo, int posicao){
        this.id = id;
        this.idTipo = idTipo;
        this.posicao = posicao;
    }

    public int getId() {
        return id;
    }

    public int getPosicao() {
        return posicao;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public String getNome() {
        if (this.id == 0) {
            return switch (this.idTipo) {
                case 0 -> "Erro de sintaxe";
                case 1 -> "Erro de lógica";
                case 2 -> "Exception";
                case 3 -> "File Not Found Exception";
                case 4 -> "Crash (aka Rebentanço)";
                case 5 -> "Duplicated Code";
                case 6 -> "Efeitos secundários";
                case 7 -> "Blue Screen of Death";
                case 8 -> "Ciclo infinito";
                default -> "Segmentation Fault";
            };
        } else {
            return switch (this.idTipo) {
                case 0 -> "Herança";
                case 1 -> "Programação funcional";
                case 2 -> "Testes unitários";
                case 3 -> "Tratamento de Excepções";
                case 4 -> "IDE";
                default -> "Ajuda Do Professor";
            };
        }
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public String images() {
        if(this.id == 0) {
            return switch (this.idTipo) {
                case 0 -> "syntax.png";
                case 1 -> "logic.png";
                case 2 -> "exception.png";
                case 3 -> "file-not-found-exception.png";
                case 4 -> "crash.png";
                case 5 -> "duplicated-code.png";
                case 6 -> "secondary-effects.png";
                case 7 -> "bsod.png";
                case 8 -> "infinite-loop.png";
                case 9 -> "core-dumped.png";
                default -> null;
            };
        }
        if (this.id == 1) {
            return switch (this.idTipo) {
                case 0 -> "inheritance.png";
                case 1 -> "functional.png";
                case 2 -> "unit-tests.png";
                case 3 -> "catch.png";
                case 4 -> "IDE.png";
                case 5 -> "ajuda-professor.png";
                default -> null;
            };
        }
        return null;
    }
}
