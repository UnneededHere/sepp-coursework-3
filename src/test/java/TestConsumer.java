import model.Consumer
import controller.Controller

public class TestConsumer extends ConsoleTest{

    @test
    @DisplayName("testing that the notify method works as intended")
    public void notifyTest(){
        Controller controller = createConsumer(controller);
        assertEquals("Message to i-would-never-steal-a@dog.xd and 01324456897: Test message",
        controller.notify("Test message"),
        "message should send normally")
    }

    @test
    @DisplayName("testing that the addBooking method works as intended")
    public void addBookingTest(){
        Controller controller = createConsumerAndBookFirstEvent(controller,1);
        assertEquals(["Puppies against depression"],
        controller.getBookings(),
        "if addBooking works the bookings from the consumer should be an array containing 'Puppies against depression'")
    }

    @test
    @DisplayName("testing that the toString method works as intended")
    public void consumerToStringTest(){
        Controller controller = createConsumerAndBookFirstEvent(controller,1);
        assertEquals("Consumer{bookings=Puppies against Depression, name='Chihuahua Fan\', phoneNumber='01324456897\', address='55.94872684464941 -3.199892044473183\', preferences=}",
        controller.toString(),
        "checks wether toString works correctly")
    }

}