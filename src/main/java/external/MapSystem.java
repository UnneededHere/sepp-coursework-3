package external;


import com.graphhopper.ResponsePath;
import com.graphhopper.util.Translation;
import com.graphhopper.util.shapes.GHPoint;
import model.TransportMode;

public interface MapSystem extends AutoCloseable {

    GHPoint convertToCoordinates(String coordinatesString);

    boolean isPointWithinMapBounds(GHPoint coordinates);

    ResponsePath routeBetweenPoints(TransportMode modeOfTransport, GHPoint firstCoordinate, GHPoint secondCoordinate);

    Translation getTranslation();
}



