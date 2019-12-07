package main;

public class DownwardsMovement implements IMovementState {
    Player context;

    public DownwardsMovement(Player context){
        this.context = context;
    }

    @Override
    public void move() {
        context.moveY(1);
    }
}