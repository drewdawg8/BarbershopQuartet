package com.doublea.barbershopquartet.BackgroundTools;

public class Barber extends User {
    private String description;
    public Barber(String firstName, String lastName, String phoneNumber, String email, String description){
        super(firstName, lastName, phoneNumber, email);
        this.description = description;
    }
}
