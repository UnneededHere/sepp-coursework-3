package command;

import controller.Context;
import model.*;
import view.IView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** dfjibhiudfbhdfihbihdfbhuidfiubhdfiubhdfuiobn
 * {@link AddEventTagCommand} allows anyone to get a list of {@link Event}s available on the system.
 * Optionally, users can specify a particular {@link LocalDate} to look up events for.
 */
public class AddEventTagCommand implements ICommand<EventTag> {
    private final String tagName;
    private final Set<String> tagValues;
    private final String defaultValue;
    private EventTag eventTagResult;

    /**
     * @param tagName   if true, the returned events will be filtered depending on the logged-in user:
     *                         for {@link Staff}s only the {@link Event}s they have created,
     *                         and for {@link Consumer}s only the {@link Event}s that match their {@link ConsumerPreferences}
     * @param tagValues if true, returned {@link Event}s will be filtered to contain only {@link Event}s with
     *                         {@link EventStatus#ACTIVE}
     * @param defaultValue       chosen date to look for events. Can be null. If not null, only {@link Event}s that are
     *                         happening on {@link #tagName} (i.e., starting, ending, or in between) will be included
     */
    public AddEventTagCommand(String tagName, Set<String> tagValues, String defaultValue) {
        this.tagName = tagName;
        this.tagValues = tagValues;
        this.defaultValue = defaultValue;
    }

    /**
     * @param context object that provides access to global application state
     * @param view    allows passing information to the user interface
     * @verifies.that if userEventsOnly is set, the current user must be logged in
     */
    @Override
    public void execute(Context context, IView view) {
        User currentUser = context.getUserState().getCurrentUser();

        if (currentUser instanceof Staff) {
            view.displayFailure(
                    "AddEventTagCommand",
                    LogStatus.ADD_EVENT_TAG_NOT_STAFF,
                    Map.of("tagName", tagName,
                            "tagValues", tagValues,
                            "defaultValue", defaultValue)
            );
            eventTagResult = null;
            return;
        }
        Map<String, EventTag> possibleTags = context.getEventState().getPossibleTags();
        if (possibleTags.containsKey(tagName)) {
            view.displayFailure(
                    "AddEventTagCommand",
                    LogStatus.ADD_EVENT_TAG_NAME_ALREADY_EXISTS,
                    Map.of("tagName", tagName,
                            "tagValues", tagValues,
                            "defaultValue", defaultValue)
            );
            eventTagResult = null;
            return;
        }
        if (tagValues.size() < 2) {
            view.displayFailure(
                    "AddEventTagCommand",
                    LogStatus.ADD_EVENT_TAG_NOT_ENOUGH_VALUES,
                    Map.of("tagName", tagName,
                            "tagValues", tagValues,
                            "defaultValue", defaultValue)
            );
            eventTagResult = null;
            return;
        }

        if (!tagValues.contains(defaultValue)) {
            view.displayFailure(
                    "AddEventTagCommand",
                    LogStatus.ADD_EVENT_TAG_DEFAULT_VALUE_INVALID,
                    Map.of("tagName", tagName,
                            "tagValues", tagValues,
                            "defaultValue", defaultValue)
            );
            eventTagResult = null;
            return;
        }

        eventTagResult = context.getEventState().createEventTag(tagName, tagValues, defaultValue);
        view.displaySuccess(
                "ListEventsCommand",
                LogStatus.ADD_EVENT_TAG_SUCCESS,
                Map.of("tagName", tagName,
                        "tagValues", tagValues,
                        "defaultValue", defaultValue,
                        "eventTag", eventTagResult)
        );
    }

    /**
     * @return List of {@link Event}s if successful and null otherwise
     */
    @Override
    public EventTag getResult() {
        return eventTagResult;
    }

    private enum LogStatus {
        ADD_EVENT_TAG_SUCCESS,
        ADD_EVENT_TAG_NOT_STAFF,
        ADD_EVENT_TAG_NAME_ALREADY_EXISTS,
        ADD_EVENT_TAG_NOT_ENOUGH_VALUES,
        ADD_EVENT_TAG_DEFAULT_VALUE_INVALID
    }
}
