package main;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GameSubject implements Subject{
    ArrayList<GameObserver> observers;
    HubConnection connection;
    static int i = 0;

    public GameSubject(String url) {
        observers = new ArrayList<>();
        connection = HubConnectionBuilder
                .create(url)
                .build();

        initHandlers();
        connection.start();
    }

    @Override
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    void initHandlers(){
        connection.on("ReceiveGameId", (gameId, meId, meX, meY) ->
        {
            for (GameObserver observer : observers) {
                observer.createGame("Your Game ID: " + gameId, meId, meX, meY);
            }
        }, String.class, Integer.class, Integer.class, Integer.class);

        connection.on("ReceiveJoinSuccess", (success, opponentId, opponentX, opponentY, meId, meX, meY) ->
        {
            for (GameObserver observer : observers) {
                observer.joinGame(success, opponentId, opponentX, opponentY, meId, meX, meY);
            }
        }, Boolean.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);

        connection.on("PlayerJoined", (opponentId, opponentX, opponentY) ->{
            for (GameObserver observer : observers) {
                observer.addPlayer(opponentId, opponentX, opponentY, false);
            }
        }, Integer.class, Integer.class, Integer.class);

        connection.on("Shoot", (xPos, yPos, xVel, yVel, type) -> {
            for (GameObserver observer : observers) {
                observer.addAmmo(xPos, yPos, xVel, yVel, type);
            }
        }, Float.class, Float.class, Float.class, Float.class, String.class);

        connection.on("Obstacle", (type, id, x, y, width, height, color) -> {
            for (GameObserver observer : observers) {
                observer.updateObstacle(type, id, x, y, width, height, color.toString());
            }
        }, String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Object.class);
        connection.on("gamepls", () -> {
            System.out.println("GAME PLS");
        });
        connection.on("nullPlayer", () -> {
            System.out.println("nullPlayer PLS");
        });
        connection.on("nullGame", () -> {
            System.out.println("nullGame PLS");
        });
        connection.on("joinGame", (isJoined) -> {
            System.out.println("joinGame PLS:  " + isJoined);
        }, Boolean.class );
        connection.on("registered", () -> {
            System.out.println("redžistered");
        });
        connection.on("OpponentChangedMovement", (movementType, currentX, currentY) ->{
            for (GameObserver observer : observers) {
                observer.setOpponentMovement(movementType, currentX, currentY);
            }
        }, String.class, Integer.class, Integer.class);
    }

    @Override
    public void send(String message, Object... args){
        connection.send(message, args);
    }

    @Override
    public boolean isAlive() {
        return connection.getConnectionState().equals(HubConnectionState.CONNECTED);
    }
}
