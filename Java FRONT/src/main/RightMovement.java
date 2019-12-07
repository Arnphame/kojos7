package main;

public class RightMovement implements IMovementState {
    Player context;

    public RightMovement(Player context){
        this.context = context;
    }

    @Override
    public void move() {
        context.moveX(1);
    }
}
