import command.BookEventCommand;
import command.CancelEventCommand;
import command.CreateEventCommand;
import controller.Controller;
import model.Event;
import model.EventTagCollection;
import model.EventType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BookEventSystemTests extends ConsoleTest{
    private Controller controller;
    @BeforeAll
    void setUp(){
        controller = createController();
    }

    @Test
    @DisplayName("Current logged-in user is not a consumer")
    void loginAsStaff(){
        createStaff(controller);
        Event event = createEvent(controller, 5,5);

        startOutputCapture();

        controller.runCommand(new BookEventCommand(event.getEventNumber(), 1));

        stopOutputCaptureAndCompare(
                "BOOK_EVENT_USER_NOT_CONSUMER"
        );

    }

    @Test
    @DisplayName("Event with given number does not exists")
    void nonExistingEventNumber(){
        createConsumer(controller);
        Event event = createEvent(controller, 5,5);

        startOutputCapture();

        controller.runCommand(new BookEventCommand(123123123, 1));

        stopOutputCaptureAndCompare(
                "BOOK_EVENT_EVENT_NOT_FOUND"
        );

    }

    @Test
    @DisplayName("Event is not active")
    void eventNotActive(){
        createConsumer(controller);
        Event event = createEvent(controller, 5,5);

        controller.runCommand(new CancelEventCommand(event.getEventNumber(), "sorry.."));

        startOutputCapture();

        controller.runCommand(new BookEventCommand(event.getEventNumber(), 1));

        stopOutputCaptureAndCompare(
                "BOOK_EVENT_EVENT_NOT_ACTIVE"
        );

    }

    @Test
    @DisplayName("Event is already over")
    void eventOver(){
        createConsumer(controller);

        CreateEventCommand eventCmd = new CreateEventCommand(
                "Puppies for sale",
                EventType.Theatre,
                5,
                6,
                "50 50",
                "selling puppies",
                LocalDateTime.now().minusHours(10),
                LocalDateTime.now().minusHours(5),
                new EventTagCollection()
        );

        controller.runCommand(eventCmd);

        Event event = eventCmd.getResult();

        startOutputCapture();

        controller.runCommand(new BookEventCommand(event.getEventNumber(), 1));

        stopOutputCaptureAndCompare(
                "BOOK_EVENT_ALREADY_OVER"
        );

    }

    @Test
    @DisplayName("Ticket number invalid")
    void invalidTicketNumber(){
        createConsumer(controller);
        Event event = createEvent(controller, 5,5);


        startOutputCapture();

        controller.runCommand(new BookEventCommand(event.getEventNumber(), -1));

        stopOutputCaptureAndCompare(
                "BOOK_EVENT_INVALID_NUM_TICKETS"
        );

    }

    @Test
    @DisplayName("No tickets left")
    void noTicketsLeft(){
        createConsumer(controller);
        Event event = createEvent(controller, 0, 5);

        startOutputCapture();
        controller.runCommand(new BookEventCommand(event.getEventNumber(), 1));

        stopOutputCaptureAndCompare(
                "BOOK_EVENT_NOT_ENOUGH_TICKETS_LEFT"
        );

    }

    @Test
    @DisplayName("Successful Booking")
    void bookingSuccess(){
        createConsumer(controller);
        Event event = createEvent(controller, 5, 5);

        startOutputCapture();
        controller.runCommand(new BookEventCommand(event.getEventNumber(), 1));

        stopOutputCaptureAndCompare(
                "BOOK_EVENT_SUCCESS"
        );

    }


}
