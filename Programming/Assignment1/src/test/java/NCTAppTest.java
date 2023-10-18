
/*
 * 
 * @author Dawid Szczesny
 * @IDNumber: 21300293
 * 
 */

package test.java;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import main.Exceptions.*;
import main.externalAPI.TestCentre;
import main.java.NCTBooking;

public class NCTAppTest {

    // Testing for a null registration number
    @Test
    public void testForNullRegistrationNumberParameter() {
        assertThrows(NullPointerException.class, () -> {
            new NCTBooking(null, TestCentre.Ballina);
        });
    }

    // Testing for a null test centre
    @Test
    public void testForNullTestCentreParameter(){
        assertThrows(NullPointerException.class, () -> {
            new NCTBooking("16-D-12345", null);
        });
    }

    // Tetsing for a null date and time
    @Test
    public void testForNullLocalDateAndTimeParameter(){
        assertThrows(NullPointerException.class, () -> {
            new NCTBooking("16-D-12345", TestCentre.Ballina, null);
        });
    }

    // Car registration number should be NN or NNN - L or LL - NNNNN to be valid
    @Test
    public void testForInvalidRegistrationNumber() {
        assertThrows(InvalidRegistrationNumberException.class, () -> {
            new NCTBooking("16-DDD", TestCentre.Ballinasloe);
        });
    }

    // Test for crrect test centre return
    @Test
    public void testForCorrectReturnForNCTCentre() {
        try {
            NCTBooking booking = new NCTBooking("18-G-12345", TestCentre.Ballinasloe);
            assertTrue(booking.getCentre().toString().equals("Ballinasloe"));
        } catch (InvalidRegistrationNumberException | IllegalDateAndTimeArgumentException e) {
            e.printStackTrace();
        }
    }

    // Test for correct test centre address
    @Test
    public void testForGetCentreAddress() {
        try {
            NCTBooking booking = new NCTBooking("16-D-12345", TestCentre.Ballina);
            assertTrue(booking.getCentreAddress()
                    .equals("Crossmolina Road, Ballina, Co. Mayo, F26 A568"));
        } catch (InvalidRegistrationNumberException | IllegalDateAndTimeArgumentException e) {
            e.printStackTrace();
        }
    }

    // Test to check that correct registration number is returned
    @Test
    public void testForCorrectReturnForGetRegistrationNumber() {
        try {
            NCTBooking booking = new NCTBooking("18-G-12345", TestCentre.Galway);
            assertTrue(booking.getRegistrationNumber().contentEquals("18-G-12345"));
        } catch (InvalidRegistrationNumberException | IllegalDateAndTimeArgumentException e) {
            e.printStackTrace();
        }
    }

    // Test to check if registration was changed does it return the changed
    // registration number
    @Test
    public void testForCorrectReturn_AlteredRegistrationNumber() {
        try {
            NCTBooking booking = new NCTBooking("18-G-12345", TestCentre.Ballinasloe);
            booking.setRegistrationNumber("01-D-78945");
            assertTrue(booking.getRegistrationNumber().contentEquals("01-D-78945"));
        } catch (InvalidRegistrationNumberException | IllegalDateAndTimeArgumentException e) {
            e.printStackTrace();
        }
    }

    // Test to check if registration number changed to invalid registration number
    // does exception throw
    @Test
    public void testForInvalidRegistrationNumber_WhenChanged() {
        assertThrows(InvalidRegistrationNumberException.class, () -> {
            NCTBooking booking = new NCTBooking("18-G-12345", TestCentre.Ballinasloe);
            booking.setRegistrationNumber("01-12D-78945");
        });
    }

    // Test to check correct return for get Date and Time
    @Test
    public void testForDateAndTimeAssigned() {
        try {
            NCTBooking booking = new NCTBooking("18-G-12345", TestCentre.Ballinasloe,
                    LocalDateTime.of(2024, 10, 10, 0, 0, 0));
            assertTrue(booking.getDateAndTime().equals(LocalDateTime.of(2024, 10, 10, 0, 0, 0)));

        } catch (InvalidRegistrationNumberException | IllegalDateAndTimeArgumentException e) {
            e.printStackTrace();
        }
    }

    // Test for correct external API Date and Time assignment to check that date is
    // in the future
    @Test
    public void testForExternalAPIDateAndTimeAssigned() {
        try {
            NCTBooking booking = new NCTBooking("18-G-12345", TestCentre.Galway);

            assertFalse(booking.getDateAndTime().isBefore(LocalDateTime.now()));

        } catch (InvalidRegistrationNumberException | IllegalDateAndTimeArgumentException e) {
            e.printStackTrace();
        }
    }

    // Test to check that exception IllegalDateAndTimeArgumentException is thrown
    // when we assign a date in the past
    @Test
    public void testForPastDateException() {
        assertThrows(IllegalDateAndTimeArgumentException.class, () -> {
            new NCTBooking("16-D-13245", TestCentre.Galway,
                    LocalDateTime.of(2001, 10, 10, 10, 0, 0));
        });
    }

    // Test to check that Date and Time assigned doesn't throw exception and is in
    // the future compared to now
    @Test
    public void testForPastDateDetection() {
        try {
            NCTBooking booking = new NCTBooking("18-G-12345", TestCentre.Galway,
                    LocalDateTime.of(2023, 12, 30, 0, 0, 0));
            assertTrue(LocalDateTime.now().isBefore(booking.getDateAndTime()));

        } catch (InvalidRegistrationNumberException | IllegalDateAndTimeArgumentException e) {
            e.printStackTrace();
        }

    }

    // Test to check for unique booking IDs
    @Test
    public void testForUniqueBookingIDs() {
        try {

            NCTBooking booking1 = new NCTBooking("18-G-12345", TestCentre.Ballinasloe);
            NCTBooking booking2 = new NCTBooking("18-G-12345", TestCentre.Ballinasloe);
            assertNotEquals(booking1.getBookingIDNumber(), booking2.getBookingIDNumber());
        } catch (InvalidRegistrationNumberException | IllegalDateAndTimeArgumentException e) {
            e.printStackTrace();
        }
    }

    // Test that the toString method of Booking correctly prints out the address
    @Test
    public void testForCorrectToStringMethod() {
        try {
            NCTBooking booking = new NCTBooking("211-G-78945", TestCentre.Galway,
                    LocalDateTime.of(2023, 12, 31, 12, 30, 0));

            assertTrue(booking.toString()
                    .contentEquals("Booking ID Number: " + booking.getBookingIDNumber() // using
                                                                                        // booking.getBookingIdNumber
                                                                                        // here as we created several
                                                                                        // Bookings already
                            + "\nRegistration Number: 211-G-78945" + "\nCentre: Galway" + "\nAddress: "
                            + "Merlin Commercial Park, Merlin Park Ln, Doughiska, H91 FNF4" + "\nDate & Time: On "
                            + "sunday, december 2023 at 12:30"));

        } catch (InvalidRegistrationNumberException | IllegalDateAndTimeArgumentException e) {
            e.printStackTrace();
        }
    }

}
