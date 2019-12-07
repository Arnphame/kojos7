package main;

public class UpwardsMovement implements IMovementState {
    Player context;

    public UpwardsMovement(Player context){
        this.context = context;
    }

    @Override
    public void move() {
        context.moveY(-1);
    }
}
