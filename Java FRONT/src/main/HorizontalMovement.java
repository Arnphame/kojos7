package main;

public class HorizontalMovement extends Movement{
    private int minBound;
    private int maxBound;
    private int dir = 1;
    public HorizontalMovement(int minBound, int maxBound){
        this.minBound = minBound;
        this.maxBound = maxBound;
    }
    public Vector move(Vector currentPos){
        if(currentPos.x == this.minBound || currentPos.x == this.maxBound)
            dir *= -1;
        return new Vector(currentPos.x + dir, currentPos.y);
    }
}
