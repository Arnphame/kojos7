package main;

import java.util.ArrayList;
import java.util.List;

public class ModifyAmmo {

    private List<ICommand> commands;
    private ICommand command;

    public ModifyAmmo() {
        commands = new ArrayList<>();
    }

    public void addCommand(ICommand command)
    {
        commands.add(command);
        command.execute();
    }

    public void undo()
    {
        commands.get(commands.size() - 1).undo();
        commands.remove(commands.size() -1);
    }
}
