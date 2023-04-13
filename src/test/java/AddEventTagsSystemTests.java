import command.AddEventTagCommand;
import controller.Controller;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class AddEventTagsSystemTests extends ConsoleTest{

    @Test
    @DisplayName("Check adding event tags is successful")
    void addTagsSuccess(){
        Controller controller = createStaffAndEvent(5,5);

        startOutputCapture();
        Set<String> valueSet = Set.of("0", "1", "2");
        controller.runCommand(new AddEventTagCommand("coolness", valueSet,"0"));
        stopOutputCaptureAndCompare(
                "ADD_EVENT_TAG_SUCCESS"
        );
    }

    @Test
    @DisplayName("Check if testing current user is a staff member is successful")
    void addTagsWithConsumerNotStaff(){
        Controller controller = createController();
        createConsumer(controller);

        startOutputCapture();
        Set<String> valueSet = Set.of("0", "1", "2");
        controller.runCommand(new AddEventTagCommand("coolness", valueSet, "0"));
        stopOutputCaptureAndCompare(
                "ADD_EVENT_TAG_NOT_STAFF"
        );
    }

    @Test
    @DisplayName("Check that tag name does not clash with existing tag names")
    void addTagsWithClashingTagNames(){
        Controller controller = createController();
        createStaff(controller);

        startOutputCapture();
        Set<String> valueSet = Set.of("0", "1", "2");
        controller.runCommand(new AddEventTagCommand("coolness", valueSet, "0"));
        controller.runCommand(new AddEventTagCommand("coolness", valueSet, "0"));
        stopOutputCaptureAndCompare(
                "ADD_EVENT_TAG_SUCCESS",
                "ADD_EVENT_TAG_NAME_ALREADY_EXISTS"
        );
    }

    @Test
    @DisplayName("Check there are at least 2 tag values")
    void addTagWithLessThanTwoValues(){
        Controller controller = createController();
        createStaff(controller);

        startOutputCapture();
        Set<String> valueSet = Set.of("0");
        controller.runCommand(new AddEventTagCommand("coolness", valueSet, "0"));
        stopOutputCaptureAndCompare(
                "ADD_EVENT_TAG_NOT_ENOUGH_VALUES"
        );
    }

    @Test
    @DisplayName("Default tag is included")
    void addTagWithoutDefaultTag(){
        Controller controller = createController();
        createStaff(controller);

        startOutputCapture();
        Set<String> valueSet = Set.of("0","1", "2");
        controller.runCommand(new AddEventTagCommand("coolness", valueSet, "10"));
        stopOutputCaptureAndCompare(
                "ADD_EVENT_TAG_DEFAULT_VALUE_INVALID"
        );
    }

}
