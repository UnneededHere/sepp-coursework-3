import command.ListEventsCommand;
import command.LogoutCommand;
import controller.Controller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ListEventsConsumerSystemTests extends ConsoleTest{

    private Controller controller;
    @BeforeAll
    void setUp(){
        controller = createController();
    }


    @Test
    @DisplayName("Must be logged on if userEventsOnly set to true")
    void userEventsTrueNotLoggedIn(){
        createConsumer(controller);

        startOutputCapture();

        controller.runCommand(new LogoutCommand());

        controller.runCommand(new ListEventsCommand(true, true, null));

        stopOutputCaptureAndCompare(
                "USER_LOGOUT_SUCCESS",
                "LIST_EVENTS_NOT_LOGGED_IN"
        );

    }

    @Test
    @DisplayName("Can be logged out if userEventsOnly set to false ")
    void userEventsFalseLoggedOff(){
        createConsumer(controller);

        startOutputCapture();

        controller.runCommand(new LogoutCommand());

        controller.runCommand(new ListEventsCommand(false, true, null));

        stopOutputCaptureAndCompare(
                "USER_LOGOUT_SUCCESS",
                "LIST_EVENTS_SUCCESS"
        );

    }

    @Test
    @DisplayName("UserEventsOnly set to true, logged in")
    void userEventsTrueLoggedOn(){
        createConsumer(controller);

        startOutputCapture();

        controller.runCommand(new ListEventsCommand(false, true, null));

        stopOutputCaptureAndCompare(
                "LIST_EVENTS_SUCCESS"
        );
    }



}
