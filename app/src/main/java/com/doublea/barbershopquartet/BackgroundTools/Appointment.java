package com.doublea.barbershopquartet.BackgroundTools;

public class Appointment {
    private String notes;
    private String URL;
    private Customer customer;

    /**
     * Constructor to create an Appointment object
     * @param notes contains notes from customer to barber
     * @param url holds url for the image the customer is trying to send to barber
     * @param customer hold the customer information, firstName, lastName, phoneNumber, email
     */
    public Appointment(String notes, String url, Customer customer){
        this.notes = notes;
        this.URL = url;
        this.customer = customer;
    }

    /**
     * Method to get the notes from customer
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
