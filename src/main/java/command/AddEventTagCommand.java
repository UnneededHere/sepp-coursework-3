package command;

import controller.Context;
import model.*;
import state.EventState;
import view.IView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * {@link AddEventTagCommand} allows a {@link Staff} member to create an {@link EventTag}.
 * This will be added to the system and to the {@link EventState}.
 */
public class AddEventTagCommand implements ICommand<EventTag> {
    private final String tagName;
    private final Set<String> tagValues;
    private final String defaultValue;
    private EventTag eventTagResult;

    /**
     * @param tagName   This is the human-readable name that the {@link EventTag} is given.
     * @param tagValues These are the list of values that the {@link EventTag} can be set to.
     * @param defaultValue   This is the default value that the {@link EventTag} is set to if a value is not specified.
     */
    public AddEventTagCommand(String tagName, Set<String> tagValues, String defaultValue) {
        this.tagName = tagName;
        this.tagValues = tagValues;
        this.defaultValue = defaultValue;
    }

    /**
     * @param context object that provides access to global application state
     * @param view    allows passing information to the user interface
     * @verifies.that the current user is a Staff member
     * @verifies.that the new tag name doesn't clash with any existing tag
     * @verifies.that there are at least 2 tag values
     * @verifies.that the default tag value is in the list of possible tag values
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
     * @return The added {@link EventTag} if successful and null otherwise
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
