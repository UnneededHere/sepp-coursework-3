package command;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Context;
import model.*;
import state.BookingState;
import state.EventState;
import state.UserState;
import view.IView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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

        Map<String, User> userList = userState.getAllUsers();
        List<Event> eventList = eventState.getAllEvents();
        Map<Event, List<Booking>> bookingList = null;

        for (Event event : eventList){
            List<Booking> bookings = bookingState.findBookingsByEventNumber(event.getEventNumber());
            bookingList.put(event, bookings);
        }


        Map<String, Object> saveList = new HashMap<>();
        saveList.put("Users", userList);
        saveList.put("Event", eventList);
        saveList.put("Booking", bookingList);



        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("save " + LocalDateTime.now().toString() + ".json"), saveList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



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
