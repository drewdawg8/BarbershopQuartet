package com.doublea.barbershopquartet.BackgroundTools;

import java.util.ArrayList;

public class Barber extends User {
    private String description;
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

    public void removeAppointment(Appointment appointment, TimeSlot timeSlot){

    }

    public void addAppointment(Appointment appointment){

    }

    public void editDescription(String description) {
        this.description = description;
    }
}
