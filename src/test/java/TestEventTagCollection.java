import java.security.cert.X509CRL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.EventTagCollection;

public class TestEventTagCollection extends ConsoleTest{

    @Test
    @DisplayName("testing that the getValueFor method works as intended")
    public void notifyTest(){
        EventTagCollection collection = new EventTagCollection();
        Assertions.assertEquals(false,
        collection.getValueFor("hasSocialDistancing"),
        "if getValueFor works the value gotten should be 'X'");
    }

    @Test
    @DisplayName("testing that the getValueFor method works as intended")
    public void notifyTest2(){
        EventTagCollection collection = new EventTagCollection();
        Assertions.assertEquals(false,
        collection.getValueFor("hasAirFiltraion"),
        "if getValueFor works the value gotten should be 'Y'");
    }
}
