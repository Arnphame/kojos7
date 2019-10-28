package main;

public class VerticalMovement extends Movement{
    private int minBound;
    private int maxBound;
    private int dir = 1;
    public VerticalMovement(int minBound, int maxBound){
        this.minBound = minBound;
        this.maxBound = maxBound;
    }
    public Vector move(Vector currentPos){
        if(currentPos.y == this.minBound || currentPos.y == this.maxBound)
            dir *= -1;
        return new Vector(currentPos.x, currentPos.y + dir);
    }
}
