import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Consumer;
import model.Event;
import model.EventStatus;
import model.EventType;
import model.Review;

public class TestEvent extends ConsoleTest{

    @Test
    @DisplayName("testing that the cancel method works as intended")
    public void cancelEventTest(){
        Event event = new Event(12345,"Test Event",EventType.Sports,100,100,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(12),LocalDateTime.now().plusHours(13),true,true,true);
        event.cancel();
        Assertions.assertEquals(EventStatus.CANCELLED,
        event.getStatus(),
        "cancel should work normally");
    }

    @Test
    @DisplayName("testing that the addReview method works as intended")
    public void addReviewTest(){
        Consumer consumer = new Consumer("John Smith", "johnsmith@gmail.com", "012345678910", "55.94872684464941 -3.199892044473183", "password");
        Event event = new Event(12345,"Test Event",EventType.Sports,100,100,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(12),LocalDateTime.now().plusHours(13),true,true,true);
        Review review = new Review(consumer, event, LocalDateTime.now(),"It was fun");
        event.addReview(review);
        Assertions.assertEquals(review,
        event.getReviews(),
        "addReview should work normally");
    }

    @Test
    @DisplayName("testing that the toString method works as intended")
    public void eventToStringTest(){
        Event event = new Event(12345,"Test Event",EventType.Sports,100,100,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(12),LocalDateTime.now().plusHours(13),true,true,true);
        Assertions.assertEquals("Event{" +
                "eventNumber=" + event.getEventNumber() +
                ", title='" + event.getTitle() + '\'' +
                ", type=" + event.getType() +
                ", numTicketsCap=" + event.getNumTicketsCap() +
                ", ticketPriceInPence=" + event.getTicketPriceInPence() +
                ", venueAddress='" + "55.94368888764689 -3.1888246174917114" + '\'' +
                ", description='" + "description" + '\'' +
                ", startDateTime=" + event.getStartDateTime() +
                ", endDateTime=" + event.getEndDateTime() +
                ", hasSocialDistancing=" + event.hasSocialDistancing() +
                ", hasAirFiltration=" + event.hasAirFiltration() +
                ", isOutdoors=" + event.isOutdoors() +
                ", status=" + event.getStatus() +
                ", numTicketsLeft=" + event.getNumTicketsLeft() +
                '}',
        event.toString(),
        "checks wether toString works correctly");
    }

}