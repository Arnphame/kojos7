package main;

public interface GameObserver {
    void createGame(String title, int playerId, int playerX, int playerY);
    void joinGame(boolean success, int opponentId, int opponentX, int opponentY, int meId, int meX, int meY);
    void addPlayer(int id, int x, int y, boolean isLocal);
    void addAmmo(float xPos, float yPos, float xVel, float yVel, String type);
    void subscribe(Subject subject);
    void updateObstacle(String type, int id, int x, int y, int width, int height, String color);
    void setOpponentMovement(String movementType, int currentX, int currentY);
}
