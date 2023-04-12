import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Booking;
import model.BookingStatus;
import model.Consumer;
import model.Event;
import model.EventType;

public class TestBooking{

    @Test
    @DisplayName("testing that the cancelByConsumer method works as intended")
    public void consumerCancelTest(){
        Consumer consumer = new Consumer("John Smith", "johnsmith@gmail.com", "012345678910", "55.94872684464941 -3.199892044473183", "password");
        Event event = new Event(12345,"Test Event",EventType.Sports,100,100,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(12),LocalDateTime.now().plusHours(13),true,true,true);
        Booking booking = new Booking(1,consumer,event,1,LocalDateTime.now());
        booking.cancelByConsumer();
        Assertions.assertEquals(BookingStatus.CancelledByConsumer,
        booking.getStatus(),
        "cancel by consumer should work normally");
    }

    @Test
    @DisplayName("testing that the cancelByConsumer method works as intended")
    public void consumerCancelTest2(){
        Consumer consumer = new Consumer("John Smith", "johnsmith@gmail.com", "012345678910", "55.94872684464941 -3.199892044473183", "password");
        Event event = new Event(12345,"Test Event",EventType.Sports,100,100,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(12),LocalDateTime.now().plusHours(13),true,true,true);
        Booking booking = new Booking(52,consumer,event,4,LocalDateTime.now());
        booking.cancelByConsumer();
        Assertions.assertEquals(BookingStatus.CancelledByConsumer,
        booking.getStatus(),
        "cancel by consumer should work normally");
    }

    @Test
    @DisplayName("testing that the cancelByProvider method works as intended")
    public void providerCancelTest(){
        Consumer consumer = new Consumer("John Smith", "johnsmith@gmail.com", "012345678910", "55.94872684464941 -3.199892044473183", "password");
        Event event = new Event(12345,"Test Event",EventType.Sports,100,100,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(12),LocalDateTime.now().plusHours(13),true,true,true);
        Booking booking = new Booking(1,consumer,event,1,LocalDateTime.now());
        booking.cancelByProvider();
        Assertions.assertEquals(BookingStatus.CancelledByProvider,
        booking.getStatus(),
        "cancel by provider should work normally");
    }

    @Test
    @DisplayName("testing that the cancelByProvider method works as intended")
    public void providerCancelTest2(){
        Consumer consumer = new Consumer("John Smith", "johnsmith@gmail.com", "012345678910", "55.94872684464941 -3.199892044473183", "password");
        Event event = new Event(12345,"Test Event",EventType.Sports,100,100,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(12),LocalDateTime.now().plusHours(13),true,true,true);
        Booking booking = new Booking(52,consumer,event,4,LocalDateTime.now());
        booking.cancelByProvider();
        Assertions.assertEquals(BookingStatus.CancelledByProvider,
        booking.getStatus(),
        "cancel by provider should work normally");
    }


    @Test
    @DisplayName("testing that the toString method works as intended")
    public void bookingToStringTest(){
        Consumer consumer = new Consumer("John Smith", "johnsmith@gmail.com", "012345678910", "55.94872684464941 -3.199892044473183", "password");
        Event event = new Event(12345,"Test Event",EventType.Sports,100,100,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(12),LocalDateTime.now().plusHours(13),true,true,true);
        Booking booking = new Booking(1,consumer,event,1,LocalDateTime.now());
        Assertions.assertEquals("Booking{status=active, bookingNumber=" + booking.getBookingNumber() + ", booker=John Smith, event=" + event + ", numTickets=1, bookingDateTime=" + event.getStartDateTime() + "}",
        booking.toString(),
        "checks wether toString works correctly");
    }

    @Test
    @DisplayName("testing that the toString method works as intended")
    public void bookingToStringTest2(){
        Consumer consumer = new Consumer("John Smith", "johnsmith@gmail.com", "012345678910", "55.94872684464941 -3.199892044473183", "password");
        Event event = new Event(12345,"Test Event",EventType.Sports,100,100,"55.94368888764689 -3.1888246174917114","description",LocalDateTime.now().plusHours(12),LocalDateTime.now().plusHours(13),true,true,true);
        Booking booking = new Booking(52,consumer,event,4,LocalDateTime.now());
        Assertions.assertEquals("Booking{status=active, bookingNumber=" + booking.getBookingNumber() + ", booker=John Smith, event=" + event + ", numTickets=4, bookingDateTime=" + event.getStartDateTime() + "}",
        booking.toString(),
        "checks wether toString works correctly");
    }
}