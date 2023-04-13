import command.CreateEventCommand;
import command.GetEventDirectionsCommand;
import command.UpdateConsumerProfileCommand;
import controller.Controller;
import model.Event;
import model.EventTagCollection;
import model.EventType;
import model.TransportMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class RequestDirectionsToVenueSystemTests extends ConsoleTest{
    private Controller controller;
    @BeforeAll
    void setUp(){
        controller = createController();
    }

    @Test
    @DisplayName("Event with given number doesnt exist")
    void nonExistentEventNumber(){
        createConsumer(controller);

        startOutputCapture();

        controller.runCommand(new GetEventDirectionsCommand(13213123, TransportMode.car));

        stopOutputCaptureAndCompare(
                "GET_DIRECTIONS_EVENT_NOT_FOUND"
        );
    }

    @Test
    @DisplayName("The event does not include venue address")
    void removeEventVenueAddress(){
        createConsumer(controller);

        CreateEventCommand eventCmd = new CreateEventCommand(
                "Puppies for sale",
                EventType.Theatre,
                5,
                6,
                null,
                "selling puppies",
                LocalDateTime.now().minusHours(10),
                LocalDateTime.now().minusHours(5),
                new EventTagCollection()
        );

        controller.runCommand(eventCmd);

        Event event = eventCmd.getResult();

        startOutputCapture();

        controller.runCommand(new GetEventDirectionsCommand(event.getEventNumber(), TransportMode.car));

        stopOutputCaptureAndCompare(
                "GET_DIRECTIONS_EVENT_ADDRESS_NULL"
        );

    }

    @Test
    @DisplayName("Current user is not a Consumer")
    void requestAsStaff(){
        createStaff(controller);

        Event event = createEvent(controller,5,5);

        startOutputCapture();

        controller.runCommand(new GetEventDirectionsCommand(event.getEventNumber(), TransportMode.car));

        stopOutputCaptureAndCompare(
                "GET_DIRECTIONS_USER_NOT_CONSUMER"
        );
    }

    @Test
    @DisplayName("Current user does not have valid address")
    void userHasNoAddress(){

        createConsumer(controller);

        startOutputCapture();

        controller.runCommand(new UpdateConsumerProfileCommand(
                "123456",
                "Andy",
                "Andyluz@hotmail.com",
                "071231123112",
                null,
                "teeheehee",
                new EventTagCollection()
        ));

        Event event = createEvent(controller,5,5);

        controller.runCommand(new GetEventDirectionsCommand(event.getEventNumber(), TransportMode.car));

        stopOutputCaptureAndCompare(
                "GET_DIRECTIONS_USER_ADDRESS_NULL"
        );
    }

    @Test
    @DisplayName("Directions requested successfully")
    void directionRequestSuccess(){
            createConsumer(controller);

            Event event = createEvent(controller,5,5);

            startOutputCapture();

            controller.runCommand(new GetEventDirectionsCommand(event.getEventNumber(), TransportMode.car));

            stopOutputCaptureAndCompare(
                    "GET_DIRECTIONS_SUCCESS"
            );

    }



}
