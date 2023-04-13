
import command.CreateEventCommand;
import command.ReviewEventCommand;
import controller.Controller;
import model.Event;
import model.EventTagCollection;
import model.EventType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


public class ReviewEventSystemTests extends ConsoleTest{



    @Test
    void createReviewSuccess(){
        Controller controller = createController();
        //create expired event which has already ended
        CreateEventCommand eventCmd = new CreateEventCommand(
                "Puppies against depression",
                EventType.Theatre,
                10,
                0,
                "55.94368888764689 -3.1888246174917114", // George Square Gardens, Edinburgh
                "Please be prepared to pay 2.50 pounds on entry",
                LocalDateTime.now().minusHours(10),
                LocalDateTime.now().minusHours(5),
                new EventTagCollection()
        );

        controller.runCommand(eventCmd);
        Event event = eventCmd.getResult();

        createConsumerAndBookFirstEvent(controller, 1);

        long eventNumber = event.getEventNumber();

        startOutputCapture();
        controller.runCommand(new ReviewEventCommand(
                eventNumber,
                "very nice - would love to see Paul Allen's event next"
        ));
        stopOutputCaptureAndCompare(
                "REVIEW_EVENT_SUCCESS"
        );
    }

    @Test
    void createReviewNoEventWithNumber(){
        Controller controller = createController();
        createEvent(controller, 10, 5);
        createConsumerAndBookFirstEvent(controller, 1);
        startOutputCapture();
        controller.runCommand(new ReviewEventCommand(
            123456789,
                "Honestly just terrible"
        ));
        stopOutputCaptureAndCompare(
                "REVIEW_EVENT_NOT_FOUND"
        );
    }

    @Test
    void createReviewEventNotOver(){
        Controller controller = createController();
        createConsumerAndBookFirstEvent(controller, 1);
        Event event = createEvent(controller, 10, 10);

        startOutputCapture();
        controller.runCommand(new ReviewEventCommand(
            event.getEventNumber(),
            "Excellent"
        ));
        stopOutputCaptureAndCompare(
                "REVIEW_EVENT_NOT_OVER"
        );
    }

    @Test
    void createEventUserNotConsumer(){
        Controller controller = createController();

    }


}
