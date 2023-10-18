
/*
 * 
 * @author Dawid Szczesny
 * @IDNumber: 21300293
 * 
 */


package main.java;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

import main.externalAPI.NCTBookingSlotWebservice;
import main.externalAPI.TestCentre;

/* 
 * 
 * Sample implementation of NCT booking slot web service
 * Which returns a date in 2024, with a random month, and date
 * 
*/

public class NCTBookingSlot implements NCTBookingSlotWebservice {

    @Override
    public LocalDateTime getBookingDateTime(TestCentre testCentre) {
        Random random = new Random();

        return LocalDateTime.of(LocalDate.of(2024, random.nextInt(1, 12), random.nextInt(1, 28)), LocalTime.now());
        

    }

}
