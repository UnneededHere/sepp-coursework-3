import controller.Controller;
import command.*;
import org.junit.jupiter.api.Test;


public class ListReviewsSystemTests extends ConsoleTest{
    @Test
    void StaffListReviewsValidTitle(){
        Controller controller = createStaffAndEvent(1, 48);
        //controller.runCommand(new LogoutCommand());
        startOutputCapture();
        //controller.runCommand(new ListEventReviewsCommand("Puppies against depression"));
        stopOutputCaptureAndCompare("LIST_EVENT_REVIEWS_SUCCESS");
    }


}
