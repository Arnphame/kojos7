package main;

public interface Subject {
    void addObserver(GameObserver observer);
    void send(String message, Object... args);
    boolean isAlive();
}
