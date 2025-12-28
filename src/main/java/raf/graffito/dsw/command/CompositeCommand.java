package raf.graffito.dsw.command;

import java.util.ArrayList;
import java.util.List;

public class CompositeCommand implements Command {
    
    private final List<Command> commands;
    
    public CompositeCommand(List<Command> commands) {
        this.commands = new ArrayList<>(commands);
    }
    
    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }
    
    @Override
    public void undo() {
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }
    
    public int getCommandCount() {
        return commands.size();
    }
}

