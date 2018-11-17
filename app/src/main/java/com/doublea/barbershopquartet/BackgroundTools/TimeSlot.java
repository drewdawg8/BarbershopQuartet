package com.doublea.barbershopquartet.BackgroundTools;

public class TimeSlot {
    private String month;
    private String day;
    private String hour;
    private String minute;

    public TimeSlot(String month, String day, String hour, String minute){
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getMonth() {
        return this.month;
    }

    public String getDay(){
        return this.day;
    }
}
