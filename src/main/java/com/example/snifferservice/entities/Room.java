package com.example.snifferservice.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "rooms")
public class Room {

    @Id
    private String id;
    @NotEmpty
    private String name;
    private List<String> sniffers;

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

    public List<String> getSniffers() {
        return sniffers;
    }

    public void setSniffers(List<String> sniffers) {
        this.sniffers = sniffers;
    }

    public void addSniffer(Sniffer s) {
        if(this.sniffers == null){
            this.sniffers = new ArrayList<>();
            this.sniffers.add(s.getId());
        }
        else
            this.sniffers.add(s.getId());
    }

    public void addsniffer(String s) {
        if(this.sniffers == null){
            this.sniffers = new ArrayList<>();
            this.sniffers.add(s);
        }
        else
            this.sniffers.add(s);
    }
}
