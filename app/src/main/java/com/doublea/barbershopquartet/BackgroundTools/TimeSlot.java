package com.doublea.barbershopquartet.BackgroundTools;

public class TimeSlot {
    private String month;
    private String day;
    private String hour;
    private String minute;
    private Appointment appointment;
    private boolean booked;
    private boolean unavailable;
    /**
     * Creates a TimeSlot object with appointment information and time and date of appointment
     * @param month Month in which appointment will occur
     * @param day Day in which appointment will occur
     * @param hour Hour of the appointment
     * @param minute Minute of the appointment
     * @param appointment Object that contains the notes, image, and customer information
     */
    public TimeSlot(String month, String day, String hour, String minute, Appointment appointment){
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.appointment = appointment;
        /* two variables to determine the availability of the TimeSlot */
        this.booked = false;
        this.unavailable = false;
    }

    /**
     * Returns the month of the appointment
     * @return String containing month
     */
    public String getMonth() {
        return this.month;
    }

    /**
     * Returns the day of the appointment
     * @return String containing day
     */
    public String getDay(){
        return this.day;
    }
    /**
     * Gets the hour that the appointment will occur
     * @return String containing hour
     */
    public String getHour() {
        return hour;
    }

    /**
     * Get minutes in which appointment will occure
     * @return String containing minutes
     */
    public String getMinute() {
        return minute;
    }

    /**
     * Method to return a String format with the time and day of appointment
     * @return String with appointment information
     */
    public String toString(){
        return this.getMonth() + ":" + this.getDay() + ":" + this.getHour() + ":" + this.getMinute();
    }

    /**
     * Method to return status of TimeSlot, if its currently booked or not
     * @return boolean True if TimeSlot is booked, false if not.
     */
    public boolean isBooked() {
         return this.booked;
    }

    /**
     * Method to return status of TimeSlot, if its available or unavailable.
     * @return boolean true if TimeSlot is unavailable, false if TimeSlot is
     * unavailable
     */
    public boolean isUnavailable() {
        return unavailable;
    }

    /**
     * Method to set the status of the TimeSlot whether its booked or not.
     * @param booked boolean to set value of boolean booked
     */
    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    /**
     * Method to set availability of TimeSlot if its available or not
     * @param unavailable boolean to set availability of TimeSlot
     */
    public void setUnavailable(boolean unavailable) {
        this.unavailable = unavailable;
    }
}
