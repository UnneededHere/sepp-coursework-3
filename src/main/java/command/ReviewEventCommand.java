package command;


import controller.Context;
import model.*;
import view.IView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * {@link ReviewEventCommand} allows {@link model.Consumer}s with at least 1 valid {@link Booking}
 * (which they didn't cancel) to create a review, if an {@link model.Event} with the corresponding
 * event number exists and the event has finished.
 */

public class ReviewEventCommand implements ICommand<Review>{
    private Review reviewResult;
    private long eventNumber;
    private String content;


    /**
     * @param eventNumber event number identifying an {@link model.Event} that was previousy made by
     * a logged in consumer
     * @param content content of the review being created by the consumer
     */
    public ReviewEventCommand(long eventNumber, String content){
        this.eventNumber = eventNumber;
        this.content = content;
    }

    /**
     * @param context object that provides access to global application state
     * @param view    allows passing information to the user interface
     * @verifies.that an event exists with the corresponding event number
     * @verifies.that the event is already ove
     * @verifies.that the current user is a logged-in Consumer
     * @verifies.that consumer had at least 1 valid booking (not cancelled by the consumer) at the event
     */
    @Override
    public void execute(Context context, IView view){

        //check if user is a logged-in consumer
        User currentUser = context.getUserState().getCurrentUser();
        if (!(currentUser instanceof Consumer)){
            view.displayFailure(
                    "ReviewEventCommand",
                    LogStatus.REVIEW_USER_NOT_CONSUMER,
                    Map.of("eventNumber", eventNumber,
                            "currentUser", currentUser != null ? currentUser : "none")
            );
            return;
        }

        Consumer consumer = (Consumer) currentUser;

        //check if event exists with the corresponding event number
        Event event = context.getEventState().findEventByNumber(eventNumber);
        if (event != null){
            view.displayFailure(
                    "ReviewEventCommand",
                    LogStatus.REVIEW_EVENT_NOT_FOUND,
                    Map.of("eventNumber", eventNumber)
            );
            return;
        }

        //check if the event is already over
        LocalDateTime endDateTime = event.getEndDateTime();
        if (LocalDateTime.now().isBefore(endDateTime)){
            view.displayFailure(
                    "ReviewEventCommand",
                    LogStatus.REVIEW_EVENT_NOT_OVER,
                    Map.of("eventNumber", eventNumber,
                            "endDateTime", endDateTime)
            );
        }

        //checks if the customer has at least 1 valid booking
        List<Booking> bookingList = context.getBookingState().findBookingsByEventNumber(eventNumber);
        Boolean hasBooking = false;
        for (Booking booking : bookingList){
            if (consumer.getBookings().contains(booking)){
                hasBooking = true;
                break;
            }
        }
        if (!hasBooking){
            view.displayFailure(
                    "ReviewEventCommand",
                    LogStatus.REVIEW_CUSTOMER_NO_VALID_BOOKING,
                    Map.of("eventNumber", eventNumber)
            );
        }

        //create review
        //still to implement Review
        //update review result here

        view.displaySuccess(
                "CancelBookingCommand",
                LogStatus.REVIEW_SUCCESS,
                Map.of("eventNumber", eventNumber,
                        "content", content)
        );


    }

    public Review getResult(){
        return reviewResult;
    }

    private enum LogStatus {
        REVIEW_SUCCESS,
        REVIEW_USER_NOT_CONSUMER,
        REVIEW_EVENT_NOT_FOUND,
        REVIEW_EVENT_NOT_OVER,
        REVIEW_CUSTOMER_NO_VALID_BOOKING
    }
}
