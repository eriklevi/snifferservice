package com.example.snifferservice.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "buildings")
public class Building {

    @Id
    private String id;
    @NotEmpty
    private String name;
    private List<String> rooms;
    private List<String> roomsNames;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRooms() {
        return rooms;
    }

    public List<String> getRoomsNames(){return this.roomsNames;}

    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        if(this.rooms == null){
            this.rooms = new ArrayList<>();
            this.rooms.add(room.getId());
        }
        else
            this.rooms.add(room.getId());
    }

    public void addRoom(String id) {
        if(this.rooms == null){
            this.rooms = new ArrayList<>();
            this.rooms.add(id);
        }
        else
            this.rooms.add(id);
    }

    public void addRoomName(String name) {
        if(this.roomsNames== null){
            this.roomsNames = new ArrayList<>();
            this.roomsNames.add(name);
        }
        else
            this.roomsNames.add(name);
    }
}
