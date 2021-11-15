package pt.ulusofona.lp2.deisiGreatGame;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestMoveCurrentPlayer {

    GameManager jogo = new GameManager();

    @Test
    public void testMoveCurrentPlayer1() {
        jogo.tamanhoTab = 79;
        jogo.programmers.add(new Programmer(1, "Tester", "Java", "Purple"));
        jogo.jogada.add(1);

        boolean atual = jogo.moveCurrentPlayer(0);

        boolean esperado = false;
        assertEquals("Expected result is " + esperado + "but was " + atual, esperado, atual);
    }

    @Test
    public void testMoveCurrentPlayer2() {
        jogo.tamanhoTab = 79;
        jogo.programmers.add(new Programmer(1, "Tester", "Java", "Purple"));
        jogo.jogada.add(1);


        boolean atual = jogo.moveCurrentPlayer(-10);

        boolean esperado = false;
        assertEquals("Expected result is " + esperado + "but was " + atual, esperado, atual);
    }

    @Test
    public void testMoveCurrentPlayer3() {
        jogo.tamanhoTab = 79;
        jogo.programmers.add(new Programmer(1, "Tester", "Java", "Purple"));
        jogo.jogada.add(1);


        boolean atual = jogo.moveCurrentPlayer(4);

        boolean esperado = true;
        assertEquals("Expected result is " + esperado + "but was " + atual, esperado, atual);
    }

    @Test
    public void testMoveCurrentPlayer4() {
        jogo.tamanhoTab = 79;
        jogo.programmers.add(new Programmer(1, "Tester", "Java", "Purple"));
        jogo.jogada.add(1);


        boolean atual = jogo.moveCurrentPlayer(6);

        boolean esperado = true;
        assertEquals("Expected result is " + esperado + "but was " + atual, esperado, atual);
    }
}