package com.doublea.barbershopquartet.BackgroundTools;

public class Customer extends User {
    public Customer(String firstName, String lastName, String phoneNumber, String email){
        super(firstName, lastName, phoneNumber, email);
    }

    public String toString(){
        return getFirstName() + " " + getLastName();
    }
}
