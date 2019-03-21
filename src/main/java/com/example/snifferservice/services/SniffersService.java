package com.example.snifferservice.services;

import com.example.snifferservice.entities.Configuration;
import com.example.snifferservice.entities.Sniffer;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface SniffersService {
    void addSniffer(Sniffer sniffer, HttpServletResponse response);
    void deleteSnifferById(String id, HttpServletResponse response);
    void updateSnifferById(String id, Sniffer sniffer, HttpServletResponse response);
    Sniffer getSnifferById(String id, HttpServletResponse response);
    List<Sniffer> getSniffersByRoom(String room, HttpServletResponse response);
    List<Sniffer> getSniffersByRoom(String room);
    List<Sniffer> getSniffersByBuilding(String building, HttpServletResponse response);
    List<Sniffer> getSniffers(HttpServletResponse response);
    Configuration getSnifferConfigurationByMacId(String macid, HttpServletResponse response);
}
