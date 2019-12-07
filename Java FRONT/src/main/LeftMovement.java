package main;

public class LeftMovement implements IMovementState {
    Player context;

    public LeftMovement(Player context){
        this.context = context;
    }

    @Override
    public void move() {
        context.moveX(-1);
    }
}
