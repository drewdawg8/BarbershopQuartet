package com.doublea.barbershopquartet.BackgroundTools;

import java.sql.Time;
import java.util.ArrayList;

public class Barber extends User {
    private String description;
    private String uid;
    private ArrayList<TimeSlot> schedule;
    private FirebaseInteraction firebaseInteraction;
    /**
     * Constructor to create Barber object, holding barber information
     * @param firstName String for Barber first name
     * @param lastName String for Barber last name
     * @param phoneNumber String for Barber phone number
     * @param email String for Barber email
     * @param description String to hold Barber description
     */
    public Barber(String firstName, String lastName, String phoneNumber, String email, String description){
        super(firstName, lastName, phoneNumber, email);
        this.description = description;
    }

    /**
     * Alternative constructor with UID included for barber ID
     * @param firstName String for Barber first name
     * @param lastName String for Barber last name
     * @param phoneNumber String for Barber phone number
     * @param email String for Barber email
     * @param description String to hold Barber description
     * @param uid String to hold Barber ID
     */
    public Barber(String firstName, String lastName, String phoneNumber, String email, String description, String uid){
        super(firstName, lastName, phoneNumber, email);
        this.description = description;
        this.uid = uid;
    }

    /**
     * Method to remove an appointment from a Barber's schedule
     * @param timeSlot TimeSlot that contains the appointment that will be edited in Database
     */
    public void removeAppointment(TimeSlot timeSlot){
        timeSlot.setAppointment(null);
        firebaseInteraction.writeTimeslot(timeSlot, this);
    }

    /**
     * Method to get the UID
     * @return String UID
     */
    public String getUid() {
        return uid;
    }

    /**
     * Method to set the UID
     * @param uid String UID
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Method to get Barber description
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to add an appointment to the Barber Schedule in one of his TimeSlots
     * @param appointment Appointment appointment
     * @param timeSlot TimeSlot where appointment will be added
     */
    public void addAppointment(Appointment appointment, TimeSlot timeSlot){
        timeSlot.setAppointment(appointment);
        firebaseInteraction.writeTimeslot(timeSlot, this);
    }

    /**
     * Method to edit the Barber description
     * @param description
     */
    public void editDescription(String description) {
        this.description = description;
    }
}
