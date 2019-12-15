package main;

public class MapColorUndoHandler extends CommandHandler {

    public MapColorUndoHandler(CommandHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    void doAction(Game game, String command) {
        MapColorUndoExpression exp = new MapColorUndoExpression();
        boolean correct = exp.interpret(command);
        if(correct)
            game.undoMapColor();
    }

    @Override
    boolean canHandle(String command) {
        return command.startsWith("undoMapColor");
    }
}