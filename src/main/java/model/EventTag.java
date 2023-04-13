package model;


import java.util.Set;

/**
 * {@link EventTag} represents a tag that can be present on an event made by a {@link Staff}.
 */

public class EventTag {
    public Set<String> values;
    public String defaultValue;

    /**
     * Create a new Event tag
     *
     * @param values     all the possible values that a tag can take.
     * @param defaultValue the default value for this tag.
     */

    public EventTag(Set<String> values,
            String defaultValue){
        this.values = values;
        this.defaultValue = defaultValue;
    }
}
