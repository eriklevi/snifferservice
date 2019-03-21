package com.example.snifferservice.services;

import com.example.snifferservice.entities.Building;
import com.example.snifferservice.entities.Room;
import com.example.snifferservice.repositories.BuildingsRepository;
import com.example.snifferservice.repositories.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingsRepository buildingsRepository;
    @Autowired
    private RoomsRepository roomsRepository;

    @Override
    public List<Room> getRoomsByBuildingId(String id, HttpServletResponse response) {
        List<Room> result = new ArrayList<Room>();
        Optional<Building> optionalBuilding = buildingsRepository.findById(id);
        if(optionalBuilding.isPresent() && optionalBuilding.get().getRooms() != null){
            for(String item : optionalBuilding.get().getRooms()){
                //In this case item is the room id
                Optional<Room> optionalRoom = roomsRepository.findById(item);
                if(optionalRoom.isPresent()){
                    result.add(optionalRoom.get());
                }
            }
            response.setStatus(200);
            return result;
        }
        else{
            response.setStatus(400);
            return null;
        }
    }

    @Override
    public List<Building> getAllBuildings(HttpServletResponse response) {
        response.setStatus(200);
        return buildingsRepository.findAll();
    }

    @Override
    public void addBuilding(Building building, HttpServletResponse response) {
        //the building name should be unique
        if(!buildingsRepository.existsByName(building.getName())){
            buildingsRepository.save(building);
            response.setStatus(200);
        }
        else{
            response.setStatus(400);
        }
    }

    @Override
    public void addRoomToBuildingById(String roomId, String buildingId, HttpServletResponse response) {
        Optional<Building> optionalBuilding = buildingsRepository.findById(buildingId);
        if(optionalBuilding.isPresent()){
            Building b = optionalBuilding.get();
            if(!b.getRooms().contains(roomId)){
                b.addRoom(roomId);
                buildingsRepository.save(b);
                response.setStatus(200);
            }
            else{
                response.setStatus(400);
            }
        }
        else{
            response.setStatus(400);
        }
    }

    @Override
    public void addRoomToBuildingById(Room room, String buildingId, HttpServletResponse response) {
        Optional<Building> optionalBuilding = buildingsRepository.findById(buildingId);
        if(optionalBuilding.isPresent()){
            Building b = optionalBuilding.get();
            if(b.getRooms() != null){
                if(!b.getRooms().contains(room.getName())){
                    //stiamo aggiungendo una nuova stanza ad un building quindi
                    //creiamo una nuova entry nel db
                    //accettiamo stanze con stesso nome ma in building diversi
                    Room savedRoom = roomsRepository.save(room);
                    b.addRoom(savedRoom.getId());
                    buildingsRepository.save(b);
                    response.setStatus(200);
                }
                else{
                    response.setStatus(400);
                }
            }
            else{
                //non sono ancora presenti rooms
                Room savedRoom = roomsRepository.save(room);
                b.addRoom(savedRoom.getId());
                buildingsRepository.save(b);
                response.setStatus(200);
            }
        }
        else{
            response.setStatus(400);
        }
    }
}
