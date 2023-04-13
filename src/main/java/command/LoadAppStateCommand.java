package command;

import controller.Context;
import model.Consumer;
import model.Staff;
import model.User;
import view.IView;

import java.util.Map;

public class LoadAppStateCommand {
    private Boolean successResult;
    private String filename;

    /**
     * @param filename with the name of the file to be imported
     */
    LoadAppStateCommand(String filename){
        this.filename = filename;
    }

    /**
     * @param context object that provides access to global application state
     * @param view    allows passing information to the user interface
     * @verifies.that currently logged in user is a staff member
     * @verifies.that For events, if tags clash (same name, different value) operation gets cancelled
     * @verifies.that email clashes result in operation getting cancelled
     * @verifies.that clashing events with same title, start/end date which are NOT identical will result in
     * cancelled operation
     * @verifies.that clashing bookings (same consumer, same event, same bookingDateTime results in operation cancel
     */
    public void execute(Context context, IView view){

        //check if logged-in user was a staff member
        User currentUser = context.getUserState().getCurrentUser();
        if (!(currentUser instanceof Staff)){
            view.displayFailure(
                    "ReviewEventCommand",
                    LogStatus.LOAD_APP_STATE_NOT_STAFF,
                    Map.of("filename", filename,
                            "currentUser", currentUser != null ? currentUser : "none")
            );
            return;
        }

        //Check if tags clash


    }

    public boolean getResult(){
        return successResult;
    }

    private enum LogStatus {
        LOAD_APP_STATE_SUCCESS,
        LOAD_APP_STATE_NOT_STAFF,
        LOAD_APP_STATE_TAG_CLASH,
        LOAD_APP_STATE_EMAIL_CLASH,
        LOAD_APP_STATE_EVENT_CLASH,
        LOAD_APP_STATE_BOOKING_CLASH
    }

}
