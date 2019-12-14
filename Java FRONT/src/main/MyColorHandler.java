package main;

public class MyColorHandler extends CommandHandler {
    public MyColorHandler(CommandHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    void doAction(Game game, String command) {
        MyColorExpression exp = new MyColorExpression();
        exp.interpret(command);
        game.setPlayerColor(true, exp.color);
    }

    @Override
    boolean canHandle(String command) {
        return command.startsWith("setMyColor");
    }
}
