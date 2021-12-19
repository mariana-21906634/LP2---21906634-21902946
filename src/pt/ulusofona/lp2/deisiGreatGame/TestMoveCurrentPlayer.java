package pt.ulusofona.lp2.deisiGreatGame;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestMoveCurrentPlayer {

    GameManager jogo = new GameManager();


    @Test
    public void testGetProgrammersAndReset() {
        GameManager jogo = new GameManager();
        jogo.programmers.put(Integer.parseInt("0"), new Programmer(Integer.parseInt("100"), "Amen", "C#", "BLUE"));
        jogo.programmers.put(Integer.parseInt("1"), new Programmer(Integer.parseInt("101"), "Zeca", "Java+", "GREEN"));
        var test1 = jogo.getProgrammers(false);

        GameManager jogo2 = new GameManager();
        jogo2.programmers.put(Integer.parseInt("4"), new Programmer(Integer.parseInt("110"), "A", "C#", "BLUE"));
        jogo2.programmers.put(Integer.parseInt("5"), new Programmer(Integer.parseInt("111"), "B", "Java+", "GREEN"));
        jogo2.programmers.put(Integer.parseInt("2"), new Programmer(Integer.parseInt("112"), "C", "C#", "BLUE"));
        jogo2.programmers.put(Integer.parseInt("3"), new Programmer(Integer.parseInt("113"), "D", "Java+", "GREEN"));
        var test2 = jogo2.getProgrammers(false);
        System.out.println(test1);
        System.out.println(test2);
       // assertEquals("Expected result is " + 2 + "but was " + test1.size(), 2, test1.size());
        assertEquals("Expected 2 result is " + 4 + "but was " + test2.size(), 4, test2.size());
    }

    @Test
    public void testMoveCurrentPlayer1() {
        jogo.tamanhoTab = 79;
        jogo.programmers.put(1, new Programmer(1, "Tester", "Java", "Purple"));
        jogo.jogada.add(1);

        boolean atual = jogo.moveCurrentPlayer(0);

        boolean esperado = false;
        assertEquals("Expected result is " + esperado + "but was " + atual, esperado, atual);
    }

    @Test
    public void testMoveCurrentPlayer2() {
        jogo.tamanhoTab = 79;
        jogo.programmers.put(1, new Programmer(1, "Tester", "Java", "Purple"));
        jogo.jogada.add(1);


        boolean atual = jogo.moveCurrentPlayer(-10);

        boolean esperado = false;
        assertEquals("Expected result is " + esperado + "but was " + atual, esperado, atual);
    }

    @Test
    public void testMoveCurrentPlayer3() {
        jogo.tamanhoTab = 79;
        jogo.programmers.put(1, new Programmer(1, "Tester", "Java", "Purple"));
        jogo.jogada.add(1);


        boolean atual = jogo.moveCurrentPlayer(4);

        boolean esperado = true;
        assertEquals("Expected result is " + esperado + "but was " + atual, esperado, atual);
    }

    @Test
    public void testMoveCurrentPlayer4() {
        jogo.tamanhoTab = 79;
        jogo.programmers.put(1, new Programmer(1, "Tester", "Java", "Purple"));
        jogo.jogada.add(1);


        boolean atual = jogo.moveCurrentPlayer(6);

        boolean esperado = true;
        assertEquals("Expected result is " + esperado + "but was " + atual, esperado, atual);
    }
}