package main;

public class StopHandler extends CommandHandler{

    public StopHandler(CommandHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    void doAction(Game game, String command) {
        StopExpression exp = new StopExpression();
        exp.interpret(command);
        if(exp.shouldBeStopped)
            System.exit(0);
    }

    @Override
    boolean canHandle(String command) {
        return command.startsWith("stop");
    }
}
