
/*
 * 
 * @author Dawid Szczesny
 * @IDNumber: 21300293
 * 
 */


package main.java;

import java.time.LocalDateTime;

import main.Exceptions.IllegalDateAndTimeArgumentException;
import main.Exceptions.InvalidRegistrationNumberException;
import main.externalAPI.TestCentre;

public class NCTApp {

    public static void main(String[] args) {
        try {

            NCTBooking booking1 = new NCTBooking("18-G-12345", TestCentre.Castlerea);
            System.out.println(booking1 + "\n\n");

            NCTBooking booking2 = new NCTBooking("18-G-12345", TestCentre.Castlerea, LocalDateTime.of(2025, 10, 10, 0, 0, 0));
            System.out.println(booking2 + "\n\n");

        } catch (InvalidRegistrationNumberException | IllegalDateAndTimeArgumentException e) {
            e.printStackTrace();
        }
    }

}