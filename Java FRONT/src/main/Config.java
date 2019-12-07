package main;

import java.awt.*;

public class Config {
    public static int gameWidth = 720;
    public static int gameHeight = 420;
    public static float maxVelocity = 10f;
    public static int arrowDmg = 20;
    public static int bulletDmg = 50;
    public static int grenadeDmg = 60;

    //static String signalR_URL = "http://7kojos.azurewebsites.net/api/signalr";
    public static String signalR_URL = "http://localhost:52179/api/signalr";

    public static Color playerColor = Color.green;
    public static Color opponentColor = Color.red;

    public static int headRadius = 6;
    public static int speedMultiplier = 3;

    public static int powerGaugeX = 5;
    public static int getPowerGaugeY = 5;
    public static int powerGaugeWidth = 80;
    public static int getPowerGaugeHeight = 15;

    public static int healthGaugeWidth = 35;
    public static int healthGaugeHeight = 7;
}
