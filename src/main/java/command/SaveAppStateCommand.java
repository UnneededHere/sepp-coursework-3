package command;

import controller.Context;
import model.Consumer;
import model.Event;
import model.Staff;
import model.User;
import state.BookingState;
import state.EventState;
import state.UserState;
import view.IView;

import java.util.Map;

public class SaveAppStateCommand implements ICommand{
    private Boolean successResult = false;
    private String filename;

    @Override
    public void execute(Context context, IView view){

        //check if user is a logged-in member of staff
        User currentUser = context.getUserState().getCurrentUser();
        if (!(currentUser instanceof Staff)){
            view.displayFailure(
                    "ReviewEventCommand",
                    LogStatus.SAVE_APP_STATE_USER_NOT_STAFF,
                    Map.of("currentUser", currentUser != null ? currentUser : "none")
            );
            return;
        }

        UserState userState = (UserState) context.getUserState();
        EventState eventState = (EventState) context.getEventState();
        BookingState bookingState = (BookingState) context.getBookingState();



    }

    @Override
    public Boolean getResult(){
        return successResult;

    }

    private enum LogStatus{
        SAVE_APP_STATE_USER_NOT_STAFF,
        SAVE_APP_STATE_SUCCESS
    }

}
