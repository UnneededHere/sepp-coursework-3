package command;

import java.util.List;
import java.util.Map;

import controller.Context;
import model.*;
import view.IView;

/**
 * {@link ListEventReviewsCommand} allows anyone to get a list of {@link Review}s available on the system for a given event.
 */
public class ListEventReviewsCommand implements ICommand<List<Review>> {
    private String eventTitle;
    private List<Review> reviews;

    /**
     * Constructor for the ListEventReviewsCommand class.
     * 
     * @param eventTitle the title of the event for which to retrieve the list of
     *                   reviews
     */
    public ListEventReviewsCommand(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    /**
     * Executes the command to retrieve the list of reviews for the event with the
     * given title.
     * 
     * @param context object that provides access to global application state
     * @param view    allows passing information to the user interface
     * @verifies.that eventTitle must refer to an existing event
     */
    @Override
    public void execute(Context context, IView view) {
        // Get all events from the event state
        List<Event> events = context.getEventState().getAllEvents();
        // Loop through all events to find the one with the matching title
        for (Event event : events) {
            if (event.getTitle() == this.eventTitle) {
                reviews = event.getReviews();
                // Display success message with the event title and reviews
                view.displaySuccess(
                        "ListEventReviewsCommand",
                        LogStatus.LIST_EVENT_REVIEWS_SUCCESS,
                        Map.of("eventTitle", eventTitle,
                                "reviews", reviews));
                return;
            }
        }
        // Display failure message for invalid event title
        view.displayFailure(
                "ListEventReviewsCommand",
                LogStatus.LIST_EVENT_REVIEWS_INVALID_EVENT_TITLE,
                Map.of("eventTitle", eventTitle));
        // Set reviews to null and return
        reviews = null;
        return;
    }

    /**
     * Returns the list of reviews for the event with the given title.
     * 
     * @return the list of reviews for the event with the given title
     */
    @Override
    public List<Review> getResult() {
        return reviews;
    }

    /**
     * Enumeration of the possible log status messages.
     */
    private enum LogStatus {
        LIST_EVENT_REVIEWS_SUCCESS,
        LIST_EVENT_REVIEWS_INVALID_EVENT_TITLE
    }

}