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
         }else{
             System.out.println("The command was not recognised.\nAll available commands are these:\n1. setMyColor [red green blue]\n2. setOpponentColor [red green blue]\n3. setMapColor [red green blue]\n4. undoMapColor\n5. stop");
         }
     }
}
