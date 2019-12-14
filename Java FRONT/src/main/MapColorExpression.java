package main;

import java.awt.*;

public class MapColorExpression implements Expression {

    public Color color;

    public MapColorExpression() {
        this.color = Color.MAGENTA;
    }

    @Override
    public void interpret(String context) {
        if(context.startsWith("setColor")) {
            String[] contextSplit = context.split(" ");
            if(contextSplit.length < 2) return;
            switch (contextSplit[1].toLowerCase()) {
                case "blue":
                    color = Color.BLUE;
                    break;
                case "red":
                    color = Color.RED;
                    break;
                case "green":
                    color = Color.GREEN;
                    break;
                default:
                    color = Color.ORANGE;
                    break;
            }
        }
    }
}
