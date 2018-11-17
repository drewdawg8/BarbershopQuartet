package com.doublea.barbershopquartet.BackgroundTools;

public class TimeSlot {
    private String month;
    private String day;
    private String hour;
    private String minute;
    private Appointment appointment;
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
}
