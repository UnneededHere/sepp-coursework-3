package command;

import com.graphhopper.ResponsePath;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.Translation;
import com.graphhopper.util.shapes.GHPoint;
import controller.Context;
import model.*;
import view.IView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

/**
 * {@link GetEventDirectionsCommand } allows {@link model.Consumer Consumers} to retrieve the directions to an
 * {@link Event}. The command applies for the currently logged-in user.
 */

public class GetEventDirectionsCommand implements ICommand<String[]>{
    private final long eventNumber;
    private final TransportMode transportMode;
    private String[] directionsResult;

    /**
     * @param eventNumber event number uniquely identifying a {@link Event} that was previously
     *                      made by a {@link Staff} member.
     * @param transportMode the method of transport that the {@link Consumer} would like the directions to show a route for.
     */

    public GetEventDirectionsCommand(long eventNumber,
                              TransportMode transportMode) {
        this.eventNumber = eventNumber;
        this.transportMode = transportMode;
    }

    /**
     * @return List of Directions if successful and null otherwise
     */

    @Override
    public String[] getResult() {
        return directionsResult;
    }

    /**
     * @param context object that provides access to global application state
     * @param view    allows passing information to the user interface
     * @verifies.that there is an event corresponding to the provided eventNumber
     * @verifies.that the event includes a venueAddress
     * @verifies.that the current user is a Consumer
     * @verifies.that the consumer's profile includes an address
     */

    @Override
    public void execute(Context context, IView view) {

        if (context.getEventState().findEventByNumber(eventNumber) == null) {
            view.displayFailure(
                    "GetEventDirectionsCommand",
                    LogStatus.GET_DIRECTIONS_EVENT_NOT_FOUND,
                    Map.of("eventNumber", eventNumber)
            );
            directionsResult = null;
            return;
        }

        Event event = context.getEventState().findEventByNumber(eventNumber);
        if (event.getAddress() == null){
            view.displayFailure(
                    "GetEventDirectionsCommand",
                    LogStatus.GET_DIRECTIONS_EVENT_ADDRESS_NULL,
                    Map.of("eventAddress", event.getAddress())
            );
            directionsResult = null;
            return;
        }

        User currentUser = context.getUserState().getCurrentUser();
        if (!(currentUser instanceof Consumer)){
            view.displayFailure(
                    "GetEventDirectionsCommand",
                    LogStatus.GET_DIRECTIONS_USER_NOT_CONSUMER,
                    Map.of("currentUser", currentUser)
            );
            directionsResult = null;
            return;
        }

        if (((Consumer) currentUser).getAddress() == null){
            view.displayFailure(
                    "GetEventDirectionsCommand",
                    LogStatus.GET_DIRECTIONS_USER_ADDRESS_NULL,
                    Map.of("userAddress", ((Consumer) currentUser).getAddress())
            );
            directionsResult = null;
            return;
        }

        String fromString = ((Consumer) currentUser).getAddress();
        GHPoint from = context.getMapSystem().convertToCoordinates(fromString);
        GHPoint to = context.getMapSystem().convertToCoordinates(event.getAddress());
        ResponsePath path = context.getMapSystem().routeBetweenPoints(transportMode, from, to);
        InstructionList il = path.getInstructions();
        Translation tr = context.getMapSystem().getTranslation();
        ArrayList<String> ar = new ArrayList<String>();
        for (Instruction instruction : il) {
            ar.add("distance " + instruction.getDistance() + " for instruction: " + instruction.getTurnDescription(tr));
        }
        directionsResult = ar.toArray(new String[0]);

    }
    private enum LogStatus {
        GET_DIRECTIONS_EVENT_NOT_FOUND,
        GET_DIRECTIONS_EVENT_ADDRESS_NULL,
        GET_DIRECTIONS_USER_NOT_CONSUMER,
        GET_DIRECTIONS_USER_ADDRESS_NULL,
    }
}
