package com.example.snifferservice.entities;

public class SnifferLocation {
    private  String id;
    private String mac;
    private String name;
    private String building;
    private String buildingId;
    private String room;
    private String roomId;

    public SnifferLocation() {
    }

    public SnifferLocation(String id, String mac, String name, String building, String buildingId, String room, String roomId) {
        this.id = id;
        this.mac = mac;
        this.name = name;
        this.building = building;
        this.buildingId = buildingId;
        this.room = room;
        this.roomId = roomId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
