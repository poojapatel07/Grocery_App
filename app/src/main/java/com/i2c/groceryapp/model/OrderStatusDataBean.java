package com.i2c.groceryapp.model;

public class OrderStatusDataBean {
    private String status;
    private CreatedAtBeanXX created_at;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CreatedAtBeanXX getCreated_at() {
        return created_at;
    }

    public void setCreated_at(CreatedAtBeanXX created_at) {
        this.created_at = created_at;
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

