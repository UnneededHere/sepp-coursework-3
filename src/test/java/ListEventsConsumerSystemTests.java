import controller.Controller;
import org.junit.jupiter.api.BeforeAll;

public class ListEventsConsumerSystemTests extends ConsoleTest{

    private Controller controller;
    @BeforeAll
    void setUp(){
        controller = createController();
    }


}