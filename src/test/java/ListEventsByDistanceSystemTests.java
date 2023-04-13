import command.ListEventsMaxDistanceCommand;
import command.LogoutCommand;
import command.UpdateConsumerProfileCommand;
import controller.Controller;
import model.Event;
import model.EventTagCollection;
import model.TransportMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ListEventsByDistanceSystemTests extends ConsoleTest{
    private Controller controller;
    @BeforeAll
    void setUp(){
        controller = createController();
    }

    @Test
    @DisplayName("Currently logged in user not consumer")
    void requestDistanceListAsStaff(){
        createStaff(controller);

        createEvent(controller,5,5);

        startOutputCapture();

        controller.runCommand(new ListEventsMaxDistanceCommand(true,
                true,
                null,
                TransportMode.car,
                100000,
                true,
                true,
                null));

        stopOutputCaptureAndCompare(
                "LIST_EVENTS_MAX_DISTANCE_NOT_CONSUMER"
        );
    }

    @Test
    @DisplayName("User has no address in profile")
    void userNoAddress(){

        createConsumer(controller);

        controller.runCommand(new UpdateConsumerProfileCommand(
                "123456",
                "Andy",
                "Andyluz@hotmail.com",
                "071231123112",
                null,
                "teeheehee",
                new EventTagCollection()
        ));

        startOutputCapture();

        Event event = createEvent(controller,5,5);

        controller.runCommand(new ListEventsMaxDistanceCommand(true,
                true,
                null,
                TransportMode.car,
                100000,
                true,
                true,
                null
        ));

        stopOutputCaptureAndCompare(
                "LIST_EVENTS_MAX_DISTANCE_NO_ADDRESS"
        );
    }

    @Test
    @DisplayName("User not logged in")
    void userLoggedOutRequestsDistanceList(){
        createConsumer(controller);

        startOutputCapture();

        controller.runCommand(new LogoutCommand());

        controller.runCommand(new ListEventsMaxDistanceCommand(
                true,
                true,
                null,
                TransportMode.car,
                100000,
                true,
                true,
                null
        ));

        stopOutputCaptureAndCompare(
                "USER_LOGOUT_SUCCESS",
                "LIST_EVENTS_MAX_DISTANCE_NOT_LOGGED_IN"
        );
    }



}
