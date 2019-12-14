package main;

public class OpponentColorHandler extends CommandHandler {

    public OpponentColorHandler(CommandHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    void doAction(Game game, String command) {
        OpponentColorExpression exp = new OpponentColorExpression();
        exp.interpret(command);
        game.setPlayerColor(false, exp.color);
    }

    @Override
    boolean canHandle(String command) {
        return command.startsWith("setOpponentColor");
    }
}
