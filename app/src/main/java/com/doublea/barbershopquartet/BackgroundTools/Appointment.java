package com.doublea.barbershopquartet.BackgroundTools;

public class Appointment {
    private String notes;
    private String URL;
    private Customer customer;

    /**
     * Appointment constructor to create appointment object
     *
     */
    public Appointment(String notes, String url, Customer customer){
        this.notes = notes;
        this.URL = url;
        this.customer = customer;
    }

    /**
     * Method to get the notes from user
     * @return String containing user notes
     */
    public String getNotes(){
        return this.notes;
    }

    /**
     * Method to get URL with image that barber will view
     * @return String with URL
     */
    public String getURL(){
        return this.URL;
    }

    /**
     * Method to get the Customer object, which contains customer
     * information
     * @return Customer with firstName, lastName, email, phoneNumber,
     */
    public Customer getCustomer() {
        return this.customer;
    }
}
