package command;

import controller.Context;
import view.IView;

public class SaveAppStateCommand implements ICommand{
    private Boolean successResult = false;
    private String filename;

    @Override
    public void execute(Context context, IView view){

    }

    @Override
    public Boolean getResult(){
        return successResult;

    }

}
