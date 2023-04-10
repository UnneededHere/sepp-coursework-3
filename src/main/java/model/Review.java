package model;

import java.time.LocalDateTime;

/**
 * {@link Review} represents a review made by a {@link Consumer} for an {@link Event}.
 */
public class Review {
    private Consumer author;
    private Event event;
    private LocalDateTime creationDateTime;
    private String content;

    /**
     * Create a new Review at with creationDateTime at current time
     *
     * @param author            Consumer who created the review
     * @param event             Review the event is for
     * @param creationDateTime  Time the review was created
     * @param content           Content of the review
     */

    public Review(Consumer author, Event event, LocalDateTime creationDateTime, String content){
        this.author = author;
        this.event = event;
        this.creationDateTime = creationDateTime;
        this.content = content;
    }

}
