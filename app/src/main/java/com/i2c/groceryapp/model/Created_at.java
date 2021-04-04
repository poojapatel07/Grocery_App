package com.i2c.groceryapp.model;

public class Created_at {
    private String date;

    private String timezone;

    private String timezone_type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezone_type() {
        return timezone_type;
    }

    public void setTimezone_type(String timezone_type) {
        this.timezone_type = timezone_type;
    }

    @Override
    public String toString() {
        return "ClassPojo [date = " + date + ", timezone = " + timezone + ", timezone_type = " + timezone_type + "]";
    }
}
