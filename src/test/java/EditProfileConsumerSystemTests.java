import command.LoginCommand;
import command.LogoutCommand;
import command.UpdateConsumerProfileCommand;
import controller.Controller;
import model.EventTagCollection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EditProfileConsumerSystemTests extends ConsoleTest{
    private Controller controller;
    @BeforeAll
    void setUp(){
        controller = createController();
    }

    @Test
    @DisplayName("Successful Edit")
    void profileEditSuccess(){

        startOutputCapture();
        controller.runCommand(new UpdateConsumerProfileCommand(
                "123456",
                "Andy",
                "Andyluz@hotmail.com",
                "071231123112",
                "55.86440964478519 -4.252880444477458",
                "teeheehee",
                new EventTagCollection()
        ));

        stopOutputCaptureAndCompare(
                "USER_UPDATE_PROFILE_SUCCESS"
        );

    }

    @Test
    @DisplayName("OldPassword is not null")
    void oldPasswordNotNull(){
        startOutputCapture();
        controller.runCommand(new UpdateConsumerProfileCommand(
                null,
                "Andy",
                "Andyluz@hotmail.com",
                "071231123112",
                "55.86440964478519 -4.252880444477458",
                "teeheehee",
                new EventTagCollection()
        ));

        stopOutputCaptureAndCompare(
                "USER_UPDATE_PROFILE_FIELDS_CANNOT_BE_NULL"
        );
    }

    @Test
    @DisplayName("NewName is not null")
    void newNameNotNull(){
        startOutputCapture();
        controller.runCommand(new UpdateConsumerProfileCommand(
                "123456",
                null,
                "Andyluz@hotmail.com",
                "071231123112",
                "55.86440964478519 -4.252880444477458",
                "teeheehee",
                new EventTagCollection()
        ));

        stopOutputCaptureAndCompare(
                "USER_UPDATE_PROFILE_FIELDS_CANNOT_BE_NULL"
        );
    }

    @Test
    @DisplayName("NewEmail is not null")
    void newEmailNotNull(){
        startOutputCapture();
        controller.runCommand(new UpdateConsumerProfileCommand(
                "123456",
                "Andy",
                null,
                "071231123112",
                "55.86440964478519 -4.252880444477458",
                "teeheehee",
                new EventTagCollection()
        ));

        stopOutputCaptureAndCompare(
                "USER_UPDATE_PROFILE_FIELDS_CANNOT_BE_NULL"
        );
    }

    @Test
    @DisplayName("newPhoneNumber is not null")
    void newPhoneNumberNotNull(){
        startOutputCapture();
        controller.runCommand(new UpdateConsumerProfileCommand(
                "123456",
                "Andy",
                "Andyluz@hotmail.com",
                null,
                "55.86440964478519 -4.252880444477458",
                "teeheehee",
                new EventTagCollection()
        ));

        stopOutputCaptureAndCompare(
                "USER_UPDATE_PROFILE_FIELDS_CANNOT_BE_NULL"
        );
    }

    @Test
    @DisplayName("newPassword is not null")
    void newPasswordNotNull(){
        startOutputCapture();
        controller.runCommand(new UpdateConsumerProfileCommand(
                "123456",
                "Andy",
                "Andyluz@hotmail.com",
                "071231123112",
                "55.86440964478519 -4.252880444477458",
                null,
                new EventTagCollection()
        ));

        stopOutputCaptureAndCompare(
                "USER_UPDATE_PROFILE_FIELDS_CANNOT_BE_NULL"
        );
    }

    @Test
    @DisplayName("Current user is logged in")
    void logOutCurrentUser(){
        createConsumer(controller);

        startOutputCapture();

        controller.runCommand(new LogoutCommand());

        stopOutputCaptureAndCompare(
                "USER_LOGOUT_SUCCESS",
                "USER_UPDATE_NOT_LOGGED_IN"
        );
    }

    @Test
    @DisplayName("Old password matches current user's password")
    void createUserChangePasswordWrongOldPassword(){
        createConsumer(controller);

        startOutputCapture();
        controller.runCommand(new UpdateConsumerProfileCommand(
                "6543421",
                "Andy",
                "Andyluz@hotmail.com",
                "071231123112",
                "55.86440964478519 -4.252880444477458",
                null,
                new EventTagCollection()
        ));

        stopOutputCaptureAndCompare(
                "USER_UPDATE_WRONG_PASSWORD"
        );

    }

    @Test
    @DisplayName("No other users with same email")
    void editToExistingEmail(){
        createConsumer(controller);

        startOutputCapture();
        controller.runCommand(new UpdateConsumerProfileCommand(
                "123456",
                "Andy",
                "i-would-never-steal-a@dog.xd",
                "071231123112",
                "55.86440964478519 -4.252880444477458",
                null,
                new EventTagCollection()
        ));

        stopOutputCaptureAndCompare(
                "USER_UPDATE_EMAIL_ALREADY_REGISTERED"
        );

    }

    @Test
    @DisplayName("New preferences only have known preferences and tags")
    void editToNonExistingPrefs(){
        createConsumer(controller);

        startOutputCapture();
        controller.runCommand(new UpdateConsumerProfileCommand(
                "123456",
                "Andy",
                "i-would-never-steal-a@dog.xd",
                "071231123112",
                "55.86440964478519 -4.252880444477458",
                null,
                new EventTagCollection("coolness:3")
        ));

        stopOutputCaptureAndCompare(
                "USER_UPDATE_TAG_DOES_NOT_EXIST"
        );

    }

    @Test
    @DisplayName("Address within map boundaries")
    void addressNotInBounds(){
        createConsumer(controller);
        startOutputCapture();

        controller.runCommand(new UpdateConsumerProfileCommand(
                "123456",
                "Andy",
                "i-would-never-steal-a@dog.xd",
                "071231123112",
                "91 91",
                null,
                new EventTagCollection()
        ));

        stopOutputCaptureAndCompare(
                "USER_UPDATE_ADDRESS_NOT_VALID"
        );
    }


}
