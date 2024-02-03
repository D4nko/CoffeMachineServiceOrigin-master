package pl.projekt.exceptions;

public class NotEnoughMilkExeption extends RuntimeException{
    public NotEnoughMilkExeption(String message){
        super(message);
    }
}
