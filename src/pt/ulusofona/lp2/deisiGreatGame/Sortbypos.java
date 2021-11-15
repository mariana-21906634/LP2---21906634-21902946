package pt.ulusofona.lp2.deisiGreatGame;

import java.util.Comparator;

public class Sortbypos implements Comparator<Programmer>
{

    public int compare(Programmer a, Programmer b)
    {
        return b.getPos() - a.getPos();
    }
}