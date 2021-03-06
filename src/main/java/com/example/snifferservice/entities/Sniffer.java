package com.example.snifferservice.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Document(collection = "sniffers")
public class Sniffer {

    @Id
    private String id;
    @Pattern(regexp = "^([0-9A-Fa-f]{2}[:]){5}([0-9A-Fa-f]{2})$")
    private String mac;
    private String macID;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @NotEmpty(message = "Building should not be empty")
    private String buildingId;
    @NotEmpty(message = "Room should not be empty")
    private String roomId;
    @NotEmpty(message = "Building should not be empty")
    private String buildingName;
    @NotEmpty(message = "Room should not be empty")
    private String roomName;
    private GeoJsonPoint location;
    private String status;
    @NotNull
    private Configuration configuration;

    public Sniffer(){}

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

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getMacID() {
        return macID;
    }

    public void setMacID(String macID) {
        this.macID = macID;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return this.name+" "+ this.buildingName+" "+ this.roomName;
    }
}
