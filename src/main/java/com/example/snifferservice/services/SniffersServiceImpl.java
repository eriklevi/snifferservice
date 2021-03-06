package com.example.snifferservice.services;


import com.example.snifferservice.entities.*;
import com.example.snifferservice.repositories.RoomsRepository;
import com.example.snifferservice.repositories.SniffersRepository;
import com.example.snifferservice.utils.UserContextFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SniffersServiceImpl implements SniffersService {

    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

    private final SniffersRepository sniffersRepository;
    private final RoomsRepository roomsRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate lbRestTemplate;

    @Autowired
    public SniffersServiceImpl(SniffersRepository sniffersRepository, RoomsRepository roomsRepository, PasswordEncoder passwordEncoder) {
        this.sniffersRepository = sniffersRepository;
        this.roomsRepository = roomsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addSniffer(Sniffer sniffer, HttpServletResponse response) {
        if(sniffer.getMac() != null) {
            if (!sniffersRepository.existsByMac(sniffer.getMac())) {
                List<Sniffer> list = getSniffersByRoom(sniffer.getRoomId());
            /*
            we need o check if there is already a sniffer with the same name inside the room
             */
                if (list == null) {
                    //we dont have any sniffer in the given room so we proceed to add it
                    Optional<Room> optionalRoom = roomsRepository.findById(sniffer.getRoomId());
                    try {
                        addProcedure(sniffer, response, optionalRoom);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        response.setStatus(400);
                    }
                } else {
                    if (list.stream()
                            .map(Sniffer::getName)
                            .anyMatch(str -> str.equals(sniffer.getName()))) {
                        //we have already a sniffer with the given name inside the room
                        response.setStatus(400);
                    } else {
                        Optional<Room> optionalRoom = roomsRepository.findById(sniffer.getRoomId());
                        try {
                            addProcedure(sniffer, response, optionalRoom);
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                            response.setStatus(400);
                        }
                    }
                }
            } else {
                response.setStatus(400);
            }
        }
        else{
            List<Sniffer> list = getSniffersByRoom(sniffer.getRoomId());
            /*
            we need o check if there is already a sniffer with the same name inside the room
             */
            if (list == null) {
                //we dont have any sniffer in the given room so we proceed to add it
                Optional<Room> optionalRoom = roomsRepository.findById(sniffer.getRoomId());
                try {
                    addProcedureWithoutMac(sniffer, response, optionalRoom);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    response.setStatus(400);
                }
            } else {
                if (list.stream()
                        .map(Sniffer::getName)
                        .anyMatch(str -> str.equals(sniffer.getName()))) {
                    //we have already a sniffer with the given name inside the room
                    response.setStatus(400);
                } else {
                    Optional<Room> optionalRoom = roomsRepository.findById(sniffer.getRoomId());
                    try {
                        addProcedureWithoutMac(sniffer, response, optionalRoom);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        response.setStatus(400);
                    }
                }
            }
        }
    }

    private void addProcedure(Sniffer sniffer, HttpServletResponse response, Optional<Room> optionalRoom) throws NoSuchAlgorithmException {
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            Sniffer newSniffer = new Sniffer();
            newSniffer.setMac(sniffer.getMac());
            newSniffer.setName(sniffer.getName());
            newSniffer.setBuildingId(sniffer.getBuildingId());
            newSniffer.setRoomId(sniffer.getRoomId());
            newSniffer.setMacID(sniffer.getMac().replace(":", ""));
            newSniffer.setLocation(sniffer.getLocation());
            newSniffer.setBuildingName(sniffer.getBuildingName());
            newSniffer.setRoomName(sniffer.getRoomName());
            newSniffer.setConfiguration(sniffer.getConfiguration());
            logger.info(newSniffer.toString());
            User u = new User();
            u.setUsername(sniffer.getMac().replace(":", ""));
            String password = newSniffer.getMac().replace(":", "") + "secret12345";
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes){
                sb.append(String.format("%02x", b));
            }
            password = sb.toString().substring(0, 12);
            u.setPassword(password);
            logger.debug("Password for " + u.getUsername() + ": " + password);
            //RestTemplate template = restTemplate.getCustomRestTemplate();
            HttpEntity<User> request = new HttpEntity<>(u);
            ResponseEntity<User> responseEntity = lbRestTemplate.exchange("http://userservice/restricted/sniffers", HttpMethod.POST, request, User.class);
            if(responseEntity.getStatusCodeValue() == 200){
                logger.info("Completed request for sniffer "+newSniffer.getMac()+ " successfully!");
                response.setStatus(200);
                Sniffer sniffer1 = sniffersRepository.insert(newSniffer);
                room.addsniffer(sniffer1.getId());//we use sniffer1 to be shure to get the sniffer id
                roomsRepository.save(room);
            } else{
                logger.error("Completed request for sniffer "+newSniffer.getMac() + " unsiccessfully!");
                response.setStatus(400);
            }
        }
        else{
            logger.error("addProcedure, Room is not present!");
            response.setStatus(400);
        }
    }

    private void addProcedureWithoutMac(Sniffer sniffer, HttpServletResponse response, Optional<Room> optionalRoom) throws NoSuchAlgorithmException {
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            Sniffer newSniffer = new Sniffer();
            newSniffer.setName(sniffer.getName());
            newSniffer.setBuildingId(sniffer.getBuildingId());
            newSniffer.setRoomId(sniffer.getRoomId());
            newSniffer.setMacID(sniffer.getMac().replace(":", ""));
            newSniffer.setLocation(sniffer.getLocation());
            newSniffer.setBuildingName(sniffer.getBuildingName());
            newSniffer.setRoomName(sniffer.getRoomName());
            newSniffer.setConfiguration(sniffer.getConfiguration());
            logger.info(newSniffer.toString());
            Sniffer sniffer1 = sniffersRepository.insert(newSniffer);
            room.addsniffer(sniffer1.getId());//we use sniffer1 to be shure to get the sniffer id
            roomsRepository.save(room);
        }
        else{
            logger.error("addProcedure, Room is not present!");
            response.setStatus(400);
        }
    }

    @Override
    public void disassociateSnifferById(String id, HttpServletResponse response) {
        Optional<Sniffer> optionalSniffer = sniffersRepository.findById(id);
        if(optionalSniffer.isPresent()){
            Sniffer s = optionalSniffer.get();
            s.setMac(null);
            s.setMacID(null);
            s.setStatus("Disassociated");
            sniffersRepository.save(s);
            response.setStatus(200);
        } else {
            response.setStatus(400);
        }
    }

    @Override
    public void updateSnifferById(String id, Sniffer sniffer, HttpServletResponse response) {
        Optional<Sniffer> optionalSniffer = sniffersRepository.findById(id);
        if(optionalSniffer.isPresent()
                && sniffer.getId().equals(id) //we have to check correspondence

        ){
            //initials check to find if the inserted data are ok
            Sniffer s = optionalSniffer.get();
            //check if there is correspondence between request id and provided sniffer id
            if(!s.getMac().equals(sniffer.getMac()) && sniffersRepository.existsByMac(sniffer.getMac())) {
                //we would have 2 sniffers with same mac, Error
                response.setStatus(400);
                return;
            }
            if(!s.getName().equals(sniffer.getName())){
                List<Sniffer> list = getSniffersByRoom(sniffer.getRoomId());
                if(list != null){
                    if (list.stream()
                            .map(Sniffer::getName)
                            .anyMatch(str -> str.equals(sniffer.getName()))) {
                        //we have already a sniffer with the given name inside the room
                        response.setStatus(400);
                        return;
                    }
                }
            }
            s.setMac(sniffer.getMac());
            s.setMacID(sniffer.getMacID());
            s.setName(sniffer.getName());
            s.setConfiguration(sniffer.getConfiguration());
            s.setStatus(null);
            sniffersRepository.save(s);
            response.setStatus(200);
        }
        else{
            response.setStatus(400);
        }
    }

    @Override
    public Sniffer getSnifferById(String id, HttpServletResponse response) {
            Optional<Sniffer> optionalSniffer = sniffersRepository.findById(id);
            if(optionalSniffer.isPresent()){
                response.setStatus(200);
                return optionalSniffer.get();
            }
            else{
                response.setStatus(400);
                return null;
            }
        }

    @Override
    public List<Sniffer> getSniffersByRoom(String room, HttpServletResponse response) {
        List<Sniffer> sniffers = sniffersRepository.findByRoomId(room);
        if(sniffers != null)
            response.setStatus(200);
        else
            response.setStatus(400);
        return sniffers;
    }

    @Override
    public List<Sniffer> getSniffersByBuilding(String building, HttpServletResponse response) {
        List<Sniffer> sniffers = sniffersRepository.findByRoomId(building);
        if(sniffers != null)
            response.setStatus(200);
        else
            response.setStatus(400);
        return sniffers;
    }

    @Override
    public List<Sniffer> getSniffers(HttpServletResponse response) {
        response.setStatus(200);
        return sniffersRepository.findAll();
    }

    @Override
    public Configuration getSnifferConfigurationByMacId(String id, HttpServletResponse response) {
        Optional<Sniffer> optionalSniffer = sniffersRepository.findByMacID(id);
        if(optionalSniffer.isPresent()){
            response.setStatus(200);
            return optionalSniffer.get().getConfiguration();
        }
        else{
            response.setStatus(400);
            return null;
        }
    }

    @Override
    public List<SnifferLocation> getSniffersLocation(HttpServletResponse response) {
        List<Sniffer> sniffers = sniffersRepository.findAll();
        return sniffers.stream()
                .filter(s -> s.getMac() != null)
                .map(s -> {
                    return new SnifferLocation(
                            s.getId(),
                            s.getMac(),
                            s.getName(),
                            s.getBuildingName(),
                            s.getBuildingId(),
                            s.getRoomName(),
                            s.getRoomId());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Sniffer> getSniffersByRoom(String room) {
        return sniffersRepository.findByRoomId(room);
    }
}
