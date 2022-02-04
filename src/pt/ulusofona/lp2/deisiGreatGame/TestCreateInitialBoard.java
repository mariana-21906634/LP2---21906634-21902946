package pt.ulusofona.lp2.deisiGreatGame;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestCreateInitialBoard {

    GameManager jogo = new GameManager();

    @Test
    public void testCreateInitialBoard1(){
        String[][] players = { {"1", "Mariana", "Python;C", "Purple"}, {"3", "Diogo", "Python;C", "Green"},
                {"5", "Miguel", "Python;C", "Purple"}, {"4", "Joao", "Kotlin;C", "Green"} };

        try {
            jogo.createInitialBoard(players, 2);
            fail();
        } catch (InvalidInitialBoardException ex) {
            assertEquals("O tamanho do tabuleiro é inválido" , ex.getMessage());
        }
    }

    @Test
    public void testCreateInitialBoard2() {
        String[][] players = {};

        try {
            jogo.createInitialBoard(players, 2);
            fail();
        } catch (InvalidInitialBoardException ex) {
            assertEquals("O número de jogadores é inválido" , ex.getMessage());
        }
    }

    @Test
    public void testCreateInitialBoard3() {
        String[][] players = { {"1", "Mariana", "Python;C", "Purple"}, {"3", "Diogo", "Python;C", "Green"},
                {"5", "Miguel", "Python;C", "Purple"}, {"4", "Joao", "Kotlin;C", "Green"} };
        String[][] abismo = { {"0", "-1", "10"}, {"0", "6", "3"}};

        try {
            jogo.createInitialBoard(players, 20, abismo);
            fail();
        } catch (InvalidInitialBoardException ex) {
            assertEquals("O abismo é inválido" , ex.getMessage());
        }
    }

    @Test
    public void testCreateInitialBoard4() {
        String[][] players = { {"1", "Mariana", "Python;C", "Purple"}, {"3", "Diogo", "Python;C", "Green"},
                {"5", "Miguel", "Python;C", "Purple"}, {"4", "Joao", "Kotlin;C", "Green"} };
        String[][] ferramenta = { {"1", "-1", "10"}, {"1", "6", "3"}};

        try {
            jogo.createInitialBoard(players, 2, ferramenta);
            fail();
        } catch (InvalidInitialBoardException ex) {
            assertEquals("A ferramenta é inválida" , ex.getMessage());
        }
    }

}
