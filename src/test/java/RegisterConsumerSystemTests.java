import command.RegisterConsumerCommand;
import controller.Controller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RegisterConsumerSystemTests extends ConsoleTest{
    private Controller controller;
    @BeforeAll
    void setUp(){
        controller = createController();
    }

    @Test
    @DisplayName("Checks no user is currently logged in")
    void registerWithUserLoggedIn(){
        createConsumer(controller);

        startOutputCapture();

        controller.runCommand(new RegisterConsumerCommand("Fred",
                "Freddyie@gmail.com",
                "07213123123",
                "37 nice street",
                "wordpass"
        ));

        stopOutputCaptureAndCompare(
                "USER_REGISTER_LOGGED_IN"
        );

    }

    @Test
    @DisplayName("name not null")
    void registerNameNull(){

        startOutputCapture();

        controller.runCommand(new RegisterConsumerCommand(null,
                "Freddyie@gmail.com",
                "07213123123",
                "37 nice street",
                "wordpass"
        ));

        stopOutputCaptureAndCompare(
                "USER_REGISTER_FIELDS_CANNOT_BE_NULL"
        );
    }

    @Test
    @DisplayName("number not null")
    void registerNumberNull(){

        startOutputCapture();

        controller.runCommand(new RegisterConsumerCommand("Freddy",
                "Freddyie@gmail.com",
                null,
                "37 nice street",
                "wordpass"
        ));

        stopOutputCaptureAndCompare(
                "USER_REGISTER_FIELDS_CANNOT_BE_NULL"
        );
    }

    @Test
    @DisplayName("Email not null")
    void registerEmailNull(){

        startOutputCapture();

        controller.runCommand(new RegisterConsumerCommand("Freddy",
                null,
                "07213123123",
                "37 nice street",
                "wordpass"
        ));

        stopOutputCaptureAndCompare(
                "USER_REGISTER_FIELDS_CANNOT_BE_NULL"
        );
    }

    @Test
    @DisplayName("Password not null")
    void registerPasswordNull(){

        startOutputCapture();

        controller.runCommand(new RegisterConsumerCommand("Freddy",
                "Freddyie@gmail.com",
                "07213123123",
                "37 nice street",
                null
        ));

        stopOutputCaptureAndCompare(
                "USER_REGISTER_FIELDS_CANNOT_BE_NULL"
        );
    }

    @Test
    @DisplayName("No current user with same email address")
    void registerWithEmailClash(){

        startOutputCapture();
        controller.runCommand(new RegisterConsumerCommand("Freddy",
                "Freddyie@gmail.com",
                "07213123123",
                "37 nice street",
                "heehee"
        ));

        //maybe have to log out here

        controller.runCommand(new RegisterConsumerCommand("Georgia",
                "Freddyie@gmail.com",
                "07213123323",
                "37 nice street",
                "teehee"
        ));

        stopOutputCaptureAndCompare(
                "REGISTER_CONSUMER_SUCCESS",
                "USER_REGISTER_EMAIL_ALREADY_REGISTERED"
        );

    }

    @Test
    @DisplayName("User register success")
    void registerSuccess(){
        startOutputCapture();
        controller.runCommand(new RegisterConsumerCommand("Freddy",
                "Freddyie@gmail.com",
                "07213123123",
                "37 nice street",
                "heehee"
        ));

        stopOutputCaptureAndCompare(
                "USER_LOGIN_SUCCESS"
        );
    }


}
