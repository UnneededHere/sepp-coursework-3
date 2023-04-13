import command.CreateEventCommand;
import command.SaveAppStateCommand;
import controller.Controller;
import model.EventTagCollection;
import model.EventType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.naming.ldap.Control;
import java.time.LocalDateTime;

public class CreateEventSystemTests extends ConsoleTest{

    private Controller controller;
    @BeforeAll
    void setUp(){
        controller = createController();
    }
    @Test
    @DisplayName("logged-in user is staff member")
    void createEventAsConsumer(){
        createConsumer(controller);


        startOutputCapture();
        createEvent(controller,5,5);

        stopOutputCaptureAndCompare(
                "CREATE_EVENT_USER_NOT_STAFF"
        );
    }

    @Test
    @DisplayName("event does not start after it ends")
    void createEventStartAfterEnd(){
        createStaff(controller);

        startOutputCapture();
        controller.runCommand(new CreateEventCommand(
                "Puppies for sale",
                EventType.Theatre,
                5,
                5,
                "55.86440964478519 -4.252880444477458",
                "selling puppies",
                LocalDateTime.now().plusHours(10),
                LocalDateTime.now().plusHours(5),
                new EventTagCollection()
        ));

        stopOutputCaptureAndCompare(
                "CREATE_EVENT_START_AFTER_END"
        );

    }

    @Test
    @DisplayName("Checks that start time is not in the past")
    void createEventWithPastStartDate(){
        createStaff(controller);

        startOutputCapture();
        controller.runCommand(new CreateEventCommand(
                "Puppies for sale",
                EventType.Theatre,
                5,
                5,
                "55.86440964478519 -4.252880444477458",
                "selling puppies",
                LocalDateTime.now().minusHours(10),
                LocalDateTime.now().plusHours(5),
                new EventTagCollection()

        ));

        stopOutputCaptureAndCompare(
                "CREATE_EVENT_IN_THE_PAST"
        );
    }

    @Test
    @DisplayName("Check no other event with same title has same start and end date")
    void SameEventTitleNotSameDates(){
        createStaff(controller);

        startOutputCapture();
        controller.runCommand(new CreateEventCommand(
                "Puppies for sale",
                EventType.Theatre,
                5,
                5,
                "55.86440964478519 -4.252880444477458",
                "selling puppies",
                LocalDateTime.now().plusHours(10),
                LocalDateTime.now().plusHours(10 + 1),
                new EventTagCollection()

        ));

        controller.runCommand(new CreateEventCommand(
                "Puppies for sale",
                EventType.Theatre,
                5,
                5,
                "55.86440964478519 -4.252880444477458",
                "selling puppies",
                LocalDateTime.now().plusHours(10),
                LocalDateTime.now().plusHours(10 + 1),
                new EventTagCollection()

        ));
        stopOutputCaptureAndCompare(
                "CREATE_EVENT_SUCCESS",
                "CREATE_EVENT_TITLE_AND_TIME_CLASH"
        );

    }

    @Test
    @DisplayName("Check if ticket price is negative")
    void ticketPriceNegative(){
        createStaff(controller);


        startOutputCapture();
        controller.runCommand(new CreateEventCommand(
                "Puppies for sale",
                EventType.Theatre,
                5,
                -6,
                "55.86440964478519 -4.252880444477458",
                "selling puppies",
                LocalDateTime.now().plusHours(10),
                LocalDateTime.now().plusHours(10 + 1),
                new EventTagCollection()
        ));
        stopOutputCaptureAndCompare(
                "CREATE_EVENT_NEGATIVE_TICKET_PRICE"
        );

    }

    @Test
    @DisplayName("Check for existence of given tags")
    void createEventNonExistentTag(){
        createStaff(controller);

        startOutputCapture();
        controller.runCommand(new CreateEventCommand(
                "Puppies for sale",
                EventType.Theatre,
                5,
                -6,
                "55.86440964478519 -4.252880444477458",
                "selling puppies",
                LocalDateTime.now().plusHours(10),
                LocalDateTime.now().plusHours(10 + 1),
                new EventTagCollection("ONE:TWO")
        ));

        stopOutputCaptureAndCompare(
                "CREATE_EVENT_TAGS_INVALID"
        );
    }

    @Test
    @DisplayName("Check given address is valid")
    void createEventInvalidAddress(){
        createStaff(controller);

        startOutputCapture();

        controller.runCommand(new CreateEventCommand(
                "Puppies for sale",
                EventType.Theatre,
                5,
                -6,
                "5 puppy avenue",
                "selling puppies",
                LocalDateTime.now().plusHours(10),
                LocalDateTime.now().plusHours(10 + 1),
                new EventTagCollection("ONE:TWO")
        ));

        stopOutputCaptureAndCompare(
                "CREATE_EVENT_ADDRESS_INVALID"
        );
    }

    @Test
    @DisplayName("Check given address is within map system boundaries")
    void createEventAddressOutsideMap(){
        createStaff(controller);

        startOutputCapture();
        controller.runCommand(new CreateEventCommand(
                "Puppies for sale",
                EventType.Theatre,
                5,
                -6,
                "89 89",
                "selling puppies",
                LocalDateTime.now().plusHours(10),
                LocalDateTime.now().plusHours(10 + 1),
                new EventTagCollection("ONE:TWO")
        ));
        stopOutputCaptureAndCompare(
                "CREATE_EVENT_ADDRESS_OUT_OF_RANGE"
                );
    }

    @Test
    @DisplayName("Successful event creation")
    void createEventNormally(){
        createStaff(controller);
        startOutputCapture();

        createEvent(controller, 5, 5);

        stopOutputCaptureAndCompare(
                "CREATE_EVENT_SUCCESS"
        );
    }



}
