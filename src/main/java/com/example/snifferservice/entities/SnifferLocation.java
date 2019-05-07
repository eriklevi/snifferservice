package com.example.snifferservice.entities;

public class SnifferLocation {
    private String mac;
    private String name;
    private String building;
    private String room;

    public SnifferLocation() {
    }

    public SnifferLocation(String mac, String name, String building, String room) {
        this.mac = mac;
        this.name = name;
        this.building = building;
        this.room = room;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
