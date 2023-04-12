package external;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.LMProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.routing.weighting.custom.CustomProfile;
import com.graphhopper.util.*;
import com.graphhopper.util.shapes.GHPoint;




public class OfflineMapSystem implements MapSystem{
    private final GraphHopper hopper;

    public OfflineMapSystem(){
        hopper = new GraphHopper();
        hopper.setOSMFile("scotland-latest.osm.pbf");
        hopper.setGraphHopperLocation("target/routing-graph-cache");
        hopper.setProfiles(new Profile("car").setVehicle("car").setWeighting("fastest"),
                new Profile("bike").setVehicle("bike").setWeighting("shortest"),
                new Profile("foot").setVehicle("foot").setWeighting("shortest"),
                new Profile("wheelchair").setVehicle("wheelchair").setWeighting("shortest")
                );
        hopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"), new CHProfile("bike"), new CHProfile("foot"), new CHProfile("wheelchair"));
        hopper.getLMPreparationHandler().setLMProfiles(new LMProfile("car"), new LMProfile("bike"), new LMProfile("foot"), new LMProfile("wheelchair"));
        hopper.importOrLoad();
    }







}
