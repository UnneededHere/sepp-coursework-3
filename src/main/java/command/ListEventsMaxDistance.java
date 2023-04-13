package command;

import controller.Context;
import model.*;
import view.IView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * {@link ListEventsMaxDistance} allows anyone to get a list of {@link Event}s available on the system, within a specified distance of a consumer.
 * Optionally, users can specify a particular {@link LocalDate} to look up events for.
 */
public class ListEventsMaxDistance extends ListEventsCommand {

    private TransportMode transportMode;
    private double maxDistance;

    private final boolean userEventsOnly;
    private final boolean activeEventsOnly;
    private final LocalDate searchDate;
    private List<Event> eventListResult;

    /**
     * @param userEventsOnly    if true, the returned events will be filtered depending on the logged-in user:
     *                          for {@link Staff}s only the {@link Event}s they have created,
     *                          and for {@link Consumer}s only the {@link Event}s that match their {@link ConsumerPreferences}
     * @param activeEventsOnly  if true, returned {@link Event}s will be filtered to contain only {@link Event}s with
     *                          {@link EventStatus#ACTIVE}
     * @param searchDate        chosen date to look for events. Can be null. If not null, only {@link Event}s that are
     *                          happening on {@link #searchDate} (i.e., starting, ending, or in between) will be included
     * @param transportMode     Specifies the specific mode of transport to consider when working out distance
     * @param maxDistance       Specifies the upper bound of distance to filter events by
     * @param userEventsOnly1
     * @param activeEventsOnly1
     * @param searchDate1
     */

    public ListEventsMaxDistance(boolean userEventsOnly, boolean activeEventsOnly, LocalDate searchDate, TransportMode transportMode, double maxDistance, boolean userEventsOnly1, boolean activeEventsOnly1, LocalDate searchDate1) {
        super(userEventsOnly, activeEventsOnly, searchDate);
        this.transportMode = transportMode;
        this.maxDistance = maxDistance;
        this.userEventsOnly = userEventsOnly1;
        this.activeEventsOnly = activeEventsOnly1;
        this.searchDate = searchDate1;
    }

    /**
     * @param context object that provides access to global application state
     * @param view    allows passing information to the user interface
     * @verifies.that the consumer has a registered address
     */
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
                                "transportMode", transportMode));
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
                                "transportMode", transportMode));
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
