import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Booking;
import model.Consumer;
import model.Event;
import model.EventType;

public class TestConsumer extends ConsoleTest{

    @Test
    @DisplayName("testing that the notify method works as intended")
    public void notifyTest(){
        Consumer consumer = new Consumer("John Smith", "johnsmith@gmail.com", "012345678910", "55.94872684464941 -3.199892044473183", "password");
        startOutputCapture();
        consumer.notify("test");
        stopOutputCaptureAndCompare(
                "Message to johnsmith@gmail.com and 012345678910: test"
        );
    }

    @Test
    @DisplayName("testing that the notify method works as intended")
    public void notifyTest2(){
        Consumer consumer = new Consumer("Lucy White", "lucywhite@gmail.com", "01987654321", "-3.199892044473183 55.94872684464941", "123456");
        startOutputCapture();
        consumer.notify("this message is longer than the first test was");
        stopOutputCaptureAndCompare(
                "Message to lucywhite@gmail.com and 01987654321: this message is longer than the first test was"
        );
    }

    @Test
    @DisplayName("testing that the addBooking method works as intended")
    public void addBookingTest(){
        Consumer consumer = new Consumer("John Smith", "johnsmith@gmail.com", "012345678910", "55.94872684464941 -3.199892044473183", "password");
        Event event = new Event(12345,"Test Event",EventType.Sports,100,100,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(12),LocalDateTime.now().plusHours(13),null);
        Booking booking = new Booking(1,consumer,event,1,LocalDateTime.now());
        consumer.addBooking(booking);
        Assertions.assertEquals(booking,
        consumer.getBookings(),
        "if addBooking works the bookings from the consumer should be just the object 'booking");
    }

    @Test
    @DisplayName("testing that the addBooking method works as intended")
    public void addBookingTest2(){
        Consumer consumer = new Consumer("John Smith", "johnsmith@gmail.com", "012345678910", "55.94872684464941 -3.199892044473183", "password");
        Event event = new Event(12345,"Test Event",EventType.Sports,100,100,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(12),LocalDateTime.now().plusHours(13),null);
        Booking booking2 = new Booking(1,consumer,event,2,LocalDateTime.now());
        consumer.addBooking(booking2);
        Assertions.assertEquals(booking2,
        consumer.getBookings(),
        "if addBooking works the bookings from the consumer should be just the object 'booking2");
    }

    @Test
    @DisplayName("testing that the toString method works as intended")
    public void consumerToStringTest(){
        Consumer consumer = new Consumer("John Smith", "johnsmith@gmail.com", "012345678910", "55.94872684464941 -3.199892044473183", "password");
        Assertions.assertEquals("Consumer{bookings=, name='John Smith\', phoneNumber='012345678910\', address='55.94872684464941 -3.199892044473183\', preferences=}",
        consumer.toString(),
        "checks wether toString works correctly");
    }

    @Test
    @DisplayName("testing that the toString method works as intended")
    public void consumerToStringTest2(){
        Consumer consumer = new Consumer("Lucy White", "lucywhite@gmail.com", "01987654321", "-3.199892044473183 55.94872684464941", "123456");
        Assertions.assertEquals("Consumer{bookings=, name='Lucy white\', phoneNumber='01987654321\', address='-3.199892044473183 55.94872684464941\', preferences=}",
        consumer.toString(),
        "checks wether toString works correctly");
    }
}