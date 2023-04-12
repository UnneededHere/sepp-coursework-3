import model.Consumer
import controller.Controller

public class TestEvent extends ConsoleTest{

    @test
    @DisplayName("testing that the cancel method works as intended")
    public void cancelEventTest(){
        Controller controller = createEvent(controller,100,100);
        controller.cancel()
        assertEquals(CANCELLED,
        controller.getStatus,
        "cancel should work normally")
    }

    @test
    @DisplayName("testing that the addReview method works as intended")
    public void cancelEventTest(){
        Controller controller = createEvent(controller,100,100);
        review = new ReviewEventCommand(controller.getEventNumber, "I had fun")
        controller.addReview(review)
        assertEquals(review,
        controller.getReviews,
        "addReview should work normally")
    }

    @test
    @DisplayName("testing that the toString method works as intended")
    public void eventToStringTest(){
        Controller controller = createConsumerAndBookFirstEvent(controller,1);
        assertEquals("Event{" +
                "eventNumber=" + controller.getEventNumber() +
                ", title='" + controller.getTitle() + '\'' +
                ", type=" + controller.getType() +
                ", numTicketsCap=" + controller.getNumTicketsCap() +
                ", ticketPriceInPence=" + controller.getTicketPriceInPence() +
                ", venueAddress='" + "55.94368888764689 -3.1888246174917114" + '\'' +
                ", description='" + "Please be prepared to pay 2.50 pounds on entry" + '\'' +
                ", startDateTime=" + controller.getStartDateTime() +
                ", endDateTime=" + controller.getEndDateTime() +
                ", hasSocialDistancing=" + controller.hasSocialDistancing() +
                ", hasAirFiltration=" + controller.hasAirFiltration() +
                ", isOutdoors=" + controller.isOutdoors() +
                ", status=" + controller.getStatus() +
                ", numTicketsLeft=" + controller.getNumTicketsLeft() +
                '}',
        controller.toString(),
        "checks wether toString works correctly")
    }

}