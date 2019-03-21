package com.example.snifferservice.controllers;

import com.example.snifferservice.entities.Building;
import com.example.snifferservice.entities.Room;
import com.example.snifferservice.services.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@Validated
@RequestMapping("/buildings")
public class BuildingsController {

    @Autowired
    private BuildingService buildingService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createBuilding(@RequestBody @Valid Building building, HttpServletResponse response){
        buildingService.addBuilding(building, response);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody
    List<Building>
    getBuildings(HttpServletResponse response){
        return buildingService.getAllBuildings(response);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value = "/{id}/rooms", method = RequestMethod.GET)
    public @ResponseBody
    List<Room>
    getRoomsByBuildingId(@PathVariable @NotEmpty String id, HttpServletResponse response){
        return buildingService.getRoomsByBuildingId(id, response);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value = "/{id}/rooms", method = RequestMethod.POST)
    public void
    addRoomToBuildingById(@RequestBody @Valid Room room, @PathVariable @NotEmpty String id, HttpServletResponse response){
        buildingService.addRoomToBuildingById(room, id, response);
    }
}
