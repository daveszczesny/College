
/*
 * 
 * @author Dawid Szczesny
 * @IDNumber: 21300293
 * 
 */


package main.externalAPI;

import java.time.LocalDateTime;

public interface NCTBookingSlotWebservice {
    public LocalDateTime getBookingDateTime(TestCentre testCentre);
}
