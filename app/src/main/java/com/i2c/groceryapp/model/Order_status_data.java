package com.i2c.groceryapp.model;

import java.util.ArrayList;

public class Order_status_data {
    private String status;

    private String created_at;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [created_at = " + created_at + ", status = " + status + "]";
    }

    public static class CreatedAtBeanXX {
        private String date;
        private int timezone_type;
        private String timezone;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getTimezone_type() {
            return timezone_type;
        }

        public void setTimezone_type(int timezone_type) {
            this.timezone_type = timezone_type;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }
    }
}
