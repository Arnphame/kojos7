package main;

import java.awt.*;

public class Config {
    public static int gameWidth = 720;
    public static int gameHeight = 420;
    public static float maxVelocity = 10f;
    public static int arrowDmg = 20;
    public static int bulletDmg = 50;
    public static int grenadeDmg = 60;
    static String signalR_URL = "http://7kojos.azurewebsites.net/api/signalr";
    //public static String signalR_URL = "http://localhost:52179/api/signalr";
    public static Color playerColor = Color.white;
}
