package main;

public class Stationary implements IMovementState{
    Player context;

    public Stationary(Player context){
        this.context = context;
    }

    @Override
    public void move() {
        return;
    }
}
