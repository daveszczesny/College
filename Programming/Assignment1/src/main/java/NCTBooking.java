
/*
 * 
 * @author Dawid Szczesny
 * @IDNumber: 21300293
 * 
 */


package main.java;

import java.time.LocalDateTime;

import main.Exceptions.*;
import main.externalAPI.TestCentre;
import main.externalAPI.TestCentreAddress;

public class NCTBooking {

    private String registrationNumber;
    private TestCentre centre;
    private String centreAddress;
    private LocalDateTime localDateTime;
    private int bookingID;

    private static int bookingCount = 0;

    public NCTBooking(String registrationNumber, TestCentre testCentre)
            throws InvalidRegistrationNumberException, IllegalDateAndTimeArgumentException, NullPointerException {
        
        if(registrationNumber == null){
            throw new NullPointerException("Registration number is null!!");
        }

        if(testCentre == null){
            throw new NullPointerException("Test centre is null!!");
        }

        setRegistrationNumber(registrationNumber);
        setCentre(testCentre);
        setCentreAddress(TestCentreAddress.getAddress(testCentre));
        setLocalDateAndTime(testCentre);
        setBookingIDNumber();
    }

    public NCTBooking(String registrationNumber, TestCentre testCentre, LocalDateTime localDateTime)
            throws InvalidRegistrationNumberException, IllegalDateAndTimeArgumentException, NullPointerException {

        this(registrationNumber, testCentre);
        if(localDateTime == null){
            throw new NullPointerException("Local date and time is null!!");
        }
        setLocalDateAndTime(localDateTime);
    }

    /*
     * Method to check for validity of the registration number
     */

    private void checkValidRegistrationNumber(String registrationNumber) throws InvalidRegistrationNumberException {

        // Split number into its 3 sections
        String[] parts = registrationNumber.split("-");

        // Check for appropiate lengths of the sections
        // Must be 3 sections,
        // The layout of the registration number must be : NN or NNN - L or LL - NNNNNN
        if (parts.length != 3 ||
                parts[0].length() < 2 || parts[0].length() > 3 ||
                parts[1].length() < 1 || parts[1].length() > 2 ||
                parts[2].length() != 5) {
            throw new InvalidRegistrationNumberException("Invalid registration number");
        }

        // Check for full Numeric or Alphabetic sections
        boolean isYearPartNumberic = true;
        boolean isCountyPartAlphabetic = true;
        boolean isRegNumberPartNumberic = true;

        for (char part : parts[0].toCharArray()) {
            if (!Character.isDigit(part)) {
                isYearPartNumberic = false;
                break;
            }
        }

        for (char part : parts[1].toCharArray()) {
            if (!Character.isLetter(part)) {
                isCountyPartAlphabetic = false;
                break;
            }
        }

        for (char part : parts[2].toCharArray()) {
            if (!Character.isDigit(part)) {
                isRegNumberPartNumberic = false;
                break;
            }
        }

        if (!isYearPartNumberic || !isCountyPartAlphabetic || !isRegNumberPartNumberic) {
            throw new InvalidRegistrationNumberException("Invalid registration number");
        }

    }

    // =====================================
    // ========== Setters ==========
    // =====================================

    public void setRegistrationNumber(String registrationNumber) throws InvalidRegistrationNumberException {
        checkValidRegistrationNumber(registrationNumber);
        this.registrationNumber = registrationNumber;
    }

    public void setCentre(TestCentre centre) {
        this.centre = centre;
    }

    public void setCentreAddress(String centreAddress) {
        this.centreAddress = centreAddress;
    }

    public void setLocalDateAndTime(TestCentre testCentre) throws IllegalDateAndTimeArgumentException {
        NCTBookingSlot slot = new NCTBookingSlot();
        setLocalDateAndTime(slot.getBookingDateTime(testCentre));
    }

    public void setLocalDateAndTime(LocalDateTime localDateTime) throws IllegalDateAndTimeArgumentException {

        if (LocalDateTime.now().isAfter(localDateTime)) {
            throw new IllegalDateAndTimeArgumentException("Date and Time in the past");
        }

        this.localDateTime = localDateTime;
    }

    public void setBookingIDNumber() {
        this.bookingID = bookingCount;
        bookingCount++;
    }

    // =====================================
    // ========== Getters ==========
    // =====================================

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public TestCentre getCentre() {
        return this.centre;
    }

    public String getCentreAddress() {
        return this.centreAddress;
    }

    public LocalDateTime getDateAndTime() {
        return localDateTime;
    }

    public int getBookingIDNumber() {
        return this.bookingID;
    }

    // ------------------------------
    // To String method

    @Override
    public String toString() {
        return "Booking ID Number: " + getBookingIDNumber() + "\nRegistration Number: " + getRegistrationNumber()
                + "\nCentre: " + getCentre() + "\nAddress: " + getCentreAddress() + "\nDate & Time: On "
                + getDateAndTime().getDayOfWeek().toString().toLowerCase() + ", "
                + getDateAndTime().getMonth().toString().toLowerCase() + " " + getDateAndTime().getYear() + " at "
                + getDateAndTime().getHour() + ":" + getDateAndTime().getMinute();
    }

}
