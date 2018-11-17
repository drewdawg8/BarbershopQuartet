package com.doublea.barbershopquartet.BackgroundTools;

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
    public Barber(String firstName, String lastName, String phoneNumber, String email, String description, String uid){
        super(firstName, lastName, phoneNumber, email);
        this.description = description;
        this.uid = uid;
    }
    public void removeAppointment(Appointment appointment, TimeSlot timeSlot){

    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addAppointment(Appointment appointment){

    }

    public void editDescription(String description) {
        this.description = description;
    }
}
