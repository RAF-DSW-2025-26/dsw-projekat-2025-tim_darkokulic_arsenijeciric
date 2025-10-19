package raf.graffito.dsw.controller;

public class ActionManager {
    private ExitAction exitAction;
    private AboutUsAction aboutAction;

    public ActionManager(){
        exitAction = new ExitAction();
        aboutAction = new AboutUsAction();
    }

    public ExitAction getExitAction(){
        return exitAction;
    }

    public AboutUsAction getAboutUsAction(){
        return aboutAction;
    }
}
