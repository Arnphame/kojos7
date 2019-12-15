package main;

public class MapColorHandler extends CommandHandler {

    public MapColorHandler(CommandHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    void doAction(Game game, String command) {
        MapColorExpression exp = new MapColorExpression();
        exp.interpret(command);

        game.createMapSnapshot();

        game.setMapColor(exp.color);
    }

    @Override
    boolean canHandle(String command) {
        return command.startsWith("setMapColor");
    }
}
