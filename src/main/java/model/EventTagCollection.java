package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EventTagCollection {
    public Map<String, String> tags;


    public EventTagCollection(){
        this.tags = new HashMap<>();
        this.tags.put("hasSocialDistancing","false" );
        this.tags.put("hasAirFiltration","false" );
        this.tags.put("venueCapacity","<20" );
    }

    public EventTagCollection(String input){
        String[] nameAndValues = input.split(",");
        for ( String nameAndValue : nameAndValues ) {
            String[] splitNameAndValue = nameAndValue.split("=");
            this.tags.put(splitNameAndValue[0], splitNameAndValue[1]);
        }
    }
}
