package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * {@link EventTagCollection} represents all the tags and their corresponding values used by an {@link Event}.
 */

public class EventTagCollection {
    public Map<String, String> tags;

    /**
     * Create a new EventTagCollection, putting the three default tags
     *
     */

    public EventTagCollection(){
        this.tags = new HashMap<>();
        this.tags.put("hasSocialDistancing","false" );
        this.tags.put("hasAirFiltration","false" );
        this.tags.put("venueCapacity","<20" );
    }

    /**
     * Create a new EventTagCollection using the tags given
     *
     * @param input     A string representation of all the tags that are wanted to be added
     */

    public EventTagCollection(String input){
        String[] nameAndValues = input.split(",");
        for ( String nameAndValue : nameAndValues ) {
            String[] splitNameAndValue = nameAndValue.split("=");
            this.tags.put(splitNameAndValue[0], splitNameAndValue[1]);
        }
    }
}
