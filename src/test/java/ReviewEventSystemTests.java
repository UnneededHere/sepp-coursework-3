
import command.ReviewEventCommand;
import controller.Controller;
import model.Event;
import org.junit.jupiter.api.Test;


public class ReviewEventSystemTests extends ConsoleTest{

    @Test
    void createReviewSuccess(){
        Controller controller = createController();
        Event event = createEvent(controller, 10, 30);
        long eventNumber = event.getEventNumber();

        startOutputCapture();
        controller.runCommand(new ReviewEventCommand(
                eventNumber,
                "very nice - would love to see Paul Allen's event next"
        ));
        stopOutputCaptureAndCompare(
                ""
        );
    }


}
