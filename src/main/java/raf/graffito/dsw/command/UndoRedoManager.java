package raf.graffito.dsw.command;

import java.util.Stack;

public class UndoRedoManager {
    
    private final Stack<Command> undoStack;
    private final Stack<Command> redoStack;
    private final int maxHistorySize;
   
    public UndoRedoManager() {
        this(50);
    }
    
    public UndoRedoManager(int maxHistorySize) {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.maxHistorySize = maxHistorySize;
    }
    
    public void execute(Command command) {
        if (command == null) {
            return;
        }
        
        command.execute();
        
       
        redoStack.clear();
        undoStack.push(command); 
        
        if (undoStack.size() > maxHistorySize) {
            undoStack.remove(0);
        }
    }
    
    public boolean undo() {
        if (undoStack.isEmpty()) {
            return false;
        }
        
        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
        return true;
    }
    
    public boolean redo() {
        if (redoStack.isEmpty()) {
            return false;
        }
        
        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
        return true;
    }
    
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
    
    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
    
    public int getUndoStackSize() {
        return undoStack.size();
    }
   
    public int getRedoStackSize() {
        return redoStack.size();
    }
}

