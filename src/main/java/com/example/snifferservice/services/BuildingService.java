package com.example.snifferservice.services;

import com.example.snifferservice.entities.Building;
import com.example.snifferservice.entities.Room;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface BuildingService {
    List<Room> getRoomsByBuildingId(String id, HttpServletResponse response);
    List<Building> getAllBuildings(HttpServletResponse response);
    void addBuilding(Building building, HttpServletResponse response);
    void addRoomToBuildingById(String roomId, String buildingId, HttpServletResponse response);
    void addRoomToBuildingById(Room room, String buildingId, HttpServletResponse response);
}
