package model;


import java.util.Set;

public class EventTag {

    public Set<String> values;
    public String defaultValue;

    public EventTag(Set<String> values,
            String defaultValue){
        this.values = values;
        this.defaultValue = defaultValue;
    }
}
