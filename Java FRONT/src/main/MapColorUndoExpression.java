package main;

public class MapColorUndoExpression implements BooleanExpression{
    public MapColorUndoExpression() {
    }

    @Override
    public boolean interpret(String context) {
        return context.equals("undoMapColor");
    }
}
