package main;


import com.microsoft.signalr.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Launcher {

    private JLabel nameLabel;
    private JTextField nameTextField;
    private JButton registerButton;
    private JLabel gameIdLabel;
    private JTextField gameIdTextField;
    private JButton joinGameButton;
    private JButton createGameButton;
    private JLabel createdGameID;
    private JPanel panel;

    private HubConnection connection;

    static Game game;

    public static void main(String[] args) {
        Launcher launcher = new Launcher();

        JFrame frame = new JFrame("Start Screen");
        frame.setContentPane(launcher.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);



        launcher.connection = HubConnectionBuilder
                .create("http://localhost:52179/api/signalr")
                .build();

        launcher.connection.on("ReceiveGameId", (gameId, meX, meY) ->
        {
            launcher.createdGameID.setText("Your Game ID: " + gameId);
            launcher.joinGameButton.setEnabled(false);
            launcher.createGameButton.setEnabled(false);
            game = new Game(gameId, 720,420, launcher.connection);
            game.addPlayer(new Player(meX, meY, Color.white, game.connection, false));

            game.start();
        }, String.class, Integer.class, Integer.class);

        launcher.connection.on("ReceiveJoinSuccess", (success, opponentX, opponentY, meX, meY) ->
        {
            if(success){
                launcher.createdGameID.setText("Game joined");
                launcher.joinGameButton.setEnabled(false);
                launcher.createGameButton.setEnabled(false);
                game = new Game("Archery", 720,420, launcher.connection);
                game.start();
                game.addPlayer(new Player(opponentX, opponentY, Color.white, game.connection, true));
                game.addPlayer(new Player(meX, meY, Color.white, game.connection, false));
            }
            else{
                launcher.createdGameID.setText("Game is full !");
            }
        }, Boolean.class, Integer.class, Integer.class, Integer.class, Integer.class);

        launcher.connection.on("PlayerJoined", (opponentX, opponentY) ->{
            game.addPlayer(new Player(opponentX, opponentY, Color.white, game.connection, true));
        }, Integer.class, Integer.class);

        launcher.connection.start();

        launcher.registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(launcher.connection.getConnectionState().compareTo(HubConnectionState.CONNECTED) == 0){
                    launcher.connection.send("RegisterClient", launcher.nameTextField.getText());
                    launcher.registerButton.setEnabled(false);
                    launcher.createdGameID.setText("Successfully registered :)");
                }else{
                    launcher.registerButton.doClick();
                }
            }
        });

        launcher.joinGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(launcher.connection.getConnectionState().compareTo(HubConnectionState.CONNECTED) == 0){
                    launcher.connection.send("JoinGame", launcher.gameIdTextField.getText());
                }else{
                    launcher.createdGameID.setText("You must register first !");
                }
            }
        });

        launcher.createGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(launcher.connection.getConnectionState().compareTo(HubConnectionState.CONNECTED) == 0){
                    launcher.connection.send("CreateGame");
                }else{
                    launcher.createdGameID.setText("You must register first !");
                }
            }
        });
    }
}
