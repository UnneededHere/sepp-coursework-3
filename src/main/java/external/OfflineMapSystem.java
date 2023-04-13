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
import com.graphhopper.util.shapes.BBox;
import com.graphhopper.util.shapes.GHPoint;
import model.TransportMode;
import state.IEventState;

import java.util.Locale;

/**
 * {@link OfflineMapSystem} is a concrete implementation of {@link MapSystem}.
 */

public class OfflineMapSystem implements MapSystem{
    private final GraphHopper hopper;

    /**
     *Initialises the mapping system using the given scotland-latest.osm file
     */

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


    @Override
    public GHPoint convertToCoordinates(String coordinatesString) {
        String[] coordinates = coordinatesString.split(" ");
        try
        {
            Double.parseDouble(coordinates[0]);
        }
        catch(NumberFormatException e)
        {
            return null;
        }
        try
        {
            Double.parseDouble(coordinates[1]);
        }
        catch(NumberFormatException e)
        {
            return null;
        }
        GHPoint ghPoint = new GHPoint(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
        return ghPoint;
    }

    @Override
    public boolean isPointWithinMapBounds(GHPoint coordinates) {
        double[] coords = {coordinates.lat, coordinates.lon};
        BBox point = new BBox(coords);
        return hopper.getBaseGraph().getBounds().contains(point);
    }

    @Override
    public ResponsePath routeBetweenPoints(TransportMode modeOfTransport, GHPoint firstCoordinate, GHPoint secondCoordinate) {
        GHRequest req = new GHRequest(firstCoordinate.lat, firstCoordinate.lon, secondCoordinate.lat, secondCoordinate.lon).
                        setProfile(modeOfTransport.name()).
                        setLocale(Locale.US);
        GHResponse rsp = hopper.route(req);
        if (rsp.hasErrors())
            throw new RuntimeException(rsp.getErrors().toString());
        ResponsePath path = rsp.getBest();
        return path;
    }

    @Override
    public Translation getTranslation() {
        return hopper.getTranslationMap().getWithFallBack(Locale.UK);
    }

    @Override
    public void close() {
    }

}
