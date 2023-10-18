
/*
 * 
 * @author Dawid Szczesny
 * @IDNumber: 21300293
 * 
 */


package main.externalAPI;

import java.util.HashMap;
import java.util.Map;


/*
 * 
 * Addresses available for the test centres
 *
 */

public class TestCentreAddress {

    private static Map<TestCentre, String> addresses = new HashMap<TestCentre, String>();
    
    // Put all addresses according to test centre
    static {

        addresses.put(TestCentre.Ballinasloe, "Unit 9, Pollboy Industrial Estate, Ballinasloe, Galway, H53 NW94");
        addresses.put(TestCentre.Ballina, "Crossmolina Road, Ballina, Co. Mayo, F26 A568");
        addresses.put(TestCentre.Castlerea, "Demesne, Co. Roscommon, F45 RK06");
        addresses.put(TestCentre.Dublin, "Naul Rd, Ballymun, Swords, Co. Dublin, K67 EH30");
        addresses.put(TestCentre.Galway, "Merlin Commercial Park, Merlin Park Ln, Doughiska, H91 FNF4");
    }

    public static String getAddress(TestCentre testCentre) {
        return addresses.get(testCentre);
    }

}
