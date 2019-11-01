package main;


import com.microsoft.signalr.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Launcher implements GameObserver{

    private JLabel nameLabel;
    private JTextField nameTextField;
    private JButton registerButton;
    private JLabel gameIdLabel;
    private JTextField gameIdTextField;
    private JButton joinGameButton;
    private JButton createGameButton;
    private JLabel createdGameID;
    private JPanel panel;

    private Subject gameSubject;
    private Game game;

    public Launcher(){
        JFrame frame = new JFrame("Start Screen");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        initButtonHandlers();
    }

    public void initButtonHandlers(){
        registerButton.addActionListener(e -> {
            if(gameSubject.isAlive()){
                gameSubject.send("RegisterClient", nameTextField.getText());
                registerButton.setEnabled(false);
                createdGameID.setText("Successfully registered :)");
            }else{
                //registerButton.doClick();
                createdGameID.setText("Server not alive");
            }
        });

        joinGameButton.addActionListener(e -> {
            if(gameSubject.isAlive()){
                gameSubject.send("JoinGame", gameIdTextField.getText());
            }else{
                createdGameID.setText("You must register first !");
            }
        });

        createGameButton.addActionListener(e -> {
            if(gameSubject.isAlive()){
                gameSubject.send("CreateGame");
            }else{
                createdGameID.setText("You must register first !");
            }
        });
    }

    @Override
    public void createGame(String title, int playerX, int playerY) {
        createdGameID.setText(title);
        joinGameButton.setEnabled(false);
        createGameButton.setEnabled(false);
        game = new Game(title, Config.gameWidth, Config.gameHeight, gameSubject, 0);
        addPlayer(playerX, playerY, true);

        game.start();
    }

    @Override
    public void joinGame(boolean success, int opponentX, int opponentY, int meX, int meY) {
        if(success){
            createdGameID.setText("Game joined");
            joinGameButton.setEnabled(false);
            createGameButton.setEnabled(false);
            game = new Game("Archery", Config.gameWidth,Config.gameHeight, gameSubject, 0);
            game.start();
            addPlayer(opponentX, opponentY, false);
            addPlayer(meX, meY, true);
        }
        else{
            createdGameID.setText("Game is full !");
        }
    }

    @Override
    public void addPlayer(int x, int y, boolean isLocal) {
        game.addPlayer(new Player(x, y, Config.playerColor, isLocal));
    }

    @Override
    public void addAmmo(float xPos, float yPos, float xVel, float yVel, String type) {
        Ammo ammo = Factory.getAmmo(type, new Vector(xPos,yPos), new Vector(xVel, yVel), 50);
        game.addAmmo(ammo);
        game.launchAmmo(ammo, false);
    }

    @Override
    public void subscribe(Subject subject) {
        subject.addObserver(this);
        this.gameSubject = subject;
    }

    @Override
    public void updateObstacle(String type, int id, int x, int y, int width, int height, String color) {
        game.updateObstacle(type, id, x, y, width, height, color);
    }

    public static void main(String[] args) {
        Subject gameSubject = new GameSubject(Config.signalR_URL);
        Launcher launcher = new Launcher();
        launcher.subscribe(gameSubject);
    }
}
