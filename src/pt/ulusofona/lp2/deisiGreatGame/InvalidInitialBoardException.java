package pt.ulusofona.lp2.deisiGreatGame;

public class InvalidInitialBoardException extends Exception{
    String message, ID;
    boolean abismo, ferramenta;


   InvalidInitialBoardException(String message, String ID, boolean abismo, boolean ferramenta){
       this.message = message;
       this.ID = ID;
       this.abismo = abismo;
       this.ferramenta = ferramenta;
   }

   InvalidInitialBoardException(String message){
       this.message = message;
       this.ID = "-1";
       this.abismo = false;
       this.ferramenta = false;
    }

    public String getMessage(){
        return message;
    }

    public boolean isInvalidAbyss(){
        return abismo;
    }

    public boolean isInvalidTool(){
        return ferramenta;
    }

    public String getTypeId(){
        return ID;
    }
}
