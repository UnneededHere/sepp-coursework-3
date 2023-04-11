import model.Booking
import controller.Controller

public class TestBooking{

    @test
    @DisplayName("testing that the cancelByConsumer method works as intended")
    public void consumerCancelTest(){
        Controller controller = createConsumerAndBookFirstEvent(controller);
        controller.cancelByConsumer()
        assertEquals(CancelledByConsumer,
        controller.getStatus,
        "cancel by consumer should work normally")
    }

    @test
    @DisplayName("testing that the cancelByProvider method works as intended")
    public void consumerCancelTest(){
        Controller controller = createConsumerAndBookFirstEvent(controller);
        controller.cancelByProvider()
        assertEquals(CancelledByProvider,
        controller.getStatus,
        "cancel by provider should work normally")
    }

    @test
    @DisplayName("testing that the toString method works as intended")
    public void bookingToStringTest(){
        Controller controller = createConsumerAndBookFirstEvent(controller,1);
        assertEquals("Booking{status=active, bookingNumber=" + controller.getBookingNumber() + ", booker=Chihuahua Fan , event=" + controller.getEvent() + ", numTickets=1, bookingDateTime=" + controller.getEvent().getStartDateTime"}",
        controller.toString(),
        "checks wether toString works correctly")
    }
}