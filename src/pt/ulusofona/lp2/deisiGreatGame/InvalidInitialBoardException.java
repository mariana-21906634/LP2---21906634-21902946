package pt.ulusofona.lp2.deisiGreatGame;

public class InvalidInitialBoardException extends Exception{
    String message;
    int abismoFerramenta;

   public InvalidInitialBoardException(){}

   public InvalidInitialBoardException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public boolean isInvalidAbyss(){
        return false;
    }

    public boolean isInvalidTool(){
        return false;
    }

    public String getTypeId(){
        return Integer.toString(abismoFerramenta);
    }
}
