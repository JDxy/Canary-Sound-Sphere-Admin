package com.admin.canarysoundsphereadmin.models;

public class EventClass {
    private String _id;
    private String logo;
    private String image;
    private String name;
    private String date;
    private String time;
    private int capacity;
    private String description;
    private String direction;
    private String marker;
    private String ticket_store;

    public EventClass(String _id, String logo, String image, String name, String date, String time, int capacity, String description, String direction, String marker, String ticket_store) {
        this._id = _id;
        this.logo = logo;
        this.image = image;
        this.name = name;
        this.date = date;
        this.time = time;
        this.capacity = capacity;
        this.description = description;
        this.direction = direction;
        this.marker = marker;
        this.ticket_store = ticket_store;
    }

    public String get_id() {
        return _id;
    }

    public String getLogo() {
        return logo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDescription() {
        return description;
    }

    public String getDirection() {
        return direction;
    }

    public String getMarker() {
        return marker;
    }

    public String getTicket_store() {
        return ticket_store;
    }
}
