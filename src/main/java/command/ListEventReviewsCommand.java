package command;

import java.util.List;
import java.util.Map;

import controller.Context;
import model.*;
import view.IView;

public class ListEventReviewsCommand implements ICommand<List<Review>> {
    private String eventTitle;
    private List<Review> reviews;
    
    public ListEventReviewsCommand(String eventTitle) {
        this.eventTitle = eventTitle;
    }
    
    @Override
    public void execute(Context context, IView view) {
        // Implementation of command logic to retrieve event reviews and add them to the reviews list
        // ...
        List<Event> events = context.getEventState().getAllEvents();
        for (Event event : events) {
            if (event.getTitle() == this.eventTitle) {
                reviews = event.getReviews();
                view.displaySuccess(
                    "ListEventReviewsCommand",
                    LogStatus.LIST_EVENT_REVIEWS_SUCCESS,
                    Map.of("eventTitle", eventTitle,
                            "reviews", reviews)
                );
                return;
            }
        } 
        view.displayFailure(
                "ListEventReviewsCommand",
                LogStatus.LIST_EVENT_REVIEWS_INVALID_EVENT_TITLE,
                Map.of("eventTitle", eventTitle)
        );
        reviews = null;
        return;
    }

    @Override
    public List<Review> getResult() {
        return reviews;
    }

    private enum LogStatus {
        LIST_EVENT_REVIEWS_SUCCESS,
        LIST_EVENT_REVIEWS_INVALID_EVENT_TITLE
    }
}