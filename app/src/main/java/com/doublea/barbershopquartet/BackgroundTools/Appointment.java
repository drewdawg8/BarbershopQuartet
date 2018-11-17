package com.doublea.barbershopquartet.BackgroundTools;

public class Appointment {
    private String notes;
    private String URL;
    private Customer customer;

    public Appointment(String notes, String url, Customer customer){
        this.notes = notes;
        this.URL = url;
        this.customer = customer;
    }

    public String getNotes(){
        return this.notes;
    }

    public String getURL(){
        return this.URL;
    }

    public Customer getCustomer() {
        return this.customer;
    }
}
