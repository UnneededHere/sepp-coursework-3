package command;

import controller.Context;
import model.*;
import view.IView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListEventsMaxDistance extends ListEventsCommand {
    private TransportMode transportMode;
    private double maxDistance;

    private final boolean userEventsOnly;
    private final boolean activeEventsOnly;
    private final LocalDate searchDate;
    private List<Event> eventListResult;

    public ListEventsMaxDistance(boolean userEventsOnly, boolean activeEventsOnly, LocalDate searchDate, TransportMode transportMode, double maxDistance) {
        super(userEventsOnly, activeEventsOnly, searchDate);
        this.transportMode = transportMode;
        this.maxDistance = maxDistance;
    }

    @Override
    public void execute(Context context, IView view) {
        User currentUser = context.getUserState().getCurrentUser();

        if (currentUser == null) {
            view.displayFailure(
                    "ListEventsMaxDistance",
                    LogStatus.LIST_EVENTS_MAX_DISTANCE_NOT_LOGGED_IN,
                    Map.of("activeEventsOnly", activeEventsOnly,
                                "userEventsOnly", true,
                                "maxDistance", maxDistance,
                                "transportMode", transportMode);
            eventListResult = null;
            return;
        }

        if (currentUser instanceof Consumer) {
            Consumer consumer = (Consumer) currentUser;
            if (consumer.getAddress() == null) {
                view.displayFailure(
                        "ListEventsMaxDistance",
                        LogStatus.LIST_EVENTS_MAX_DISTANCE_NO_ADDRESS,
                        Map.of("activeEventsOnly", activeEventsOnly,
                                "userEventsOnly", true,
                                "maxDistance", maxDistance,
                                "transportMode", transportMode);
                eventListResult = null;
                return;
            }
            Map<String, EventTag> possibleTags = context.getEventState().getPossibleTags();
            EventTagCollection preferences = consumer.getPreferences();
            List<Event> eventsFittingPreferences = context.getEventState().getAllEvents().stream()
                    .filter(event -> (eventSatisfiesPreferences(possibleTags, preferences, event)
                            && (maxDistance >= getDistance(context, view, event))))
                    .collect(Collectors.toList());

            eventListResult = filterEvents(eventsFittingPreferences, activeEventsOnly, searchDate);
            view.displaySuccess(
                    "ListEventsMaxDistance",
                    LogStatus.LIST_EVENTS_MAX_DISTANCE_SUCCESS,
                    Map.of("activeEventsOnly", activeEventsOnly,
                            "userEventsOnly", true,
                            "searchDate", String.valueOf(searchDate),
                            "eventList", eventListResult,
                            "transportMode", transportMode,
                            "maxDistance", maxDistance));
            return;
        }
    }

    private Double getDistance(Context context, IView view, Event event) {
        GetEventDirectionsCommand getEventDirections = new GetEventDirectionsCommand(event.getEventNumber(), transportMode);
        getEventDirections.execute(context, view);
        return Double.parseDouble(getEventDirections.getResult()[0]);
    }

    private enum LogStatus {
        LIST_EVENTS_MAX_DISTANCE_SUCCESS,
        LIST_EVENTS_MAX_DISTANCE_NOT_LOGGED_IN,
        LIST_EVENTS_MAX_DISTANCE_NO_ADDRESS
    }
}
