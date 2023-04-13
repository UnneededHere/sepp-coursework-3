import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Consumer;
import model.Event;
import model.EventStatus;
import model.EventTagCollection;
import model.EventType;
import model.Review;

public class TestEvent extends ConsoleTest{

    @Test
    @DisplayName("testing that the cancel method works as intended")
    public void cancelEventTest(){
        Event event = new Event(12345,"Test Event",EventType.Sports,100,100,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(12),LocalDateTime.now().plusHours(13),null);
        event.cancel();
        Assertions.assertEquals(EventStatus.CANCELLED,
        event.getStatus(),
        "cancel should work normally");
    }

    @Test
    @DisplayName("testing that the cancel method works as intended")
    public void cancelEventTest2(){
        Event event = new Event(54321,"Test Event",EventType.Music,1000,700,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(1),LocalDateTime.now().plusHours(2),null);
        event.cancel();
        Assertions.assertEquals(EventStatus.CANCELLED,
        event.getStatus(),
        "cancel should work normally");
    }

    @Test
    @DisplayName("testing that the addReview method works as intended")
    public void addReviewTest(){
        Consumer consumer = new Consumer("John Smith", "johnsmith@gmail.com", "012345678910", "55.94872684464941 -3.199892044473183", "password");
        Event event = new Event(12345,"Test Event",EventType.Sports,100,100,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(12),LocalDateTime.now().plusHours(13),null);
        Review review = new Review(consumer, event, LocalDateTime.now(),"It was fun");
        event.addReview(review);
        Assertions.assertEquals(review,
        event.getReviews(),
        "addReview should work normally");
    }

    @Test
    @DisplayName("testing that the addReview method works as intended")
    public void addReviewTest2(){
        Consumer consumer = new Consumer("John Smith", "johnsmith@gmail.com", "012345678910", "55.94872684464941 -3.199892044473183", "password");
        Event event = new Event(12345,"Test Event",EventType.Sports,100,100,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(12),LocalDateTime.now().plusHours(13),null);
        Review review2 = new Review(consumer, event, LocalDateTime.now(),"This was the worst hour I have lived on this earth");
        event.addReview(review2);
        Assertions.assertEquals(review2,
        event.getReviews(),
        "addReview should work normally");
    }

    @Test
    @DisplayName("testing that the toString method works as intended")
    public void eventToStringTest(){
        EventTagCollection collection = new EventTagCollection();
        Event event = new Event(12345,"Test Event",EventType.Sports,100,100,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(12),LocalDateTime.now().plusHours(13),collection);
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
                ", tags=" + event.getTags() +
                ", status=" + event.getStatus() +
                ", numTicketsLeft=" + event.getNumTicketsLeft() +
                '}',
        event.toString(),
        "checks wether toString works correctly");
    }

    @Test
    @DisplayName("testing that the toString method works as intended")
    public void eventToStringTest2(){
        EventTagCollection collection = new EventTagCollection();
        Event event = new Event(54321,"Test Event",EventType.Music,1000,700,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(1),LocalDateTime.now().plusHours(2),collection);
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
                ", tags=" + event.getTags() +
                ", status=" + event.getStatus() +
                ", numTicketsLeft=" + event.getNumTicketsLeft() +
                '}',
        event.toString(),
        "checks wether toString works correctly");
    }

}