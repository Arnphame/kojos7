package main;

public abstract class CommandHandler {
    private CommandHandler nextHandler;

     public CommandHandler(CommandHandler nextHandler){
         this.nextHandler = nextHandler;
     }

     public void handle(Game game, String command){
         if(canHandle(command)){
             doAction(game, command);
         }else{
             passToNext(game, command);
         }
     }

     abstract void doAction(Game game, String command);

     abstract boolean canHandle(String command);

     private void passToNext(Game game, String command){
         if(nextHandler != null){
             nextHandler.handle(game, command);
         }
     }
}
