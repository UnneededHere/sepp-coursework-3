package external;


import com.graphhopper.ResponsePath;
import com.graphhopper.util.Translation;
import com.graphhopper.util.shapes.GHPoint;
import model.Booking;
import model.TransportMode;
import state.IBookingState;

/**
 * {@link MapSystem} is an interface representing the portion of application state that contains all the
 * relevant mapping information. The "scotland-latest.osm" file was stored in this external folder.
 */

public interface MapSystem extends AutoCloseable {
    /**
     * Convert a string of coordinates to a {@link GHPoint}
     *
     * @param coordinatesString the coordinated stored as a String
     * @return {@link GHPoint} the corresponding coordinates in {@link GHPoint} form.
     */
    GHPoint convertToCoordinates(String coordinatesString);

    /**
     * Checks if a {@link GHPoint} is within the Map Boundaries.
     *
     * @param coordinates the coordinates of the {@link GHPoint} to be checked.
     * @return {@link boolean} true if the point is within the map and false if not.
     */

    boolean isPointWithinMapBounds(GHPoint coordinates);

    /**
     * Calculates the route between two {@link GHPoint}s on the given {@link TransportMode}.
     *
     * @param modeOfTransport the {@link TransportMode} that the directions are required for.
     * @param firstCoordinate the {@link GHPoint} of the current address of the User.
     * @param secondCoordinate the {@link GHPoint} of the address of the {@link model.Event} that they need directions to.
     * @return {@link ResponsePath} the path to the desired {@link model.Event}.
     */

    ResponsePath routeBetweenPoints(TransportMode modeOfTransport, GHPoint firstCoordinate, GHPoint secondCoordinate);

    /**
     * Translates the directions into the appropriate language.
     *
     * @return {@link Translation} The directions now translated into the right language.
     */


    Translation getTranslation();
}



