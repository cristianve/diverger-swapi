package com.diverger.swapi.infraestructure.dto;


import lombok.Data;

import java.util.List;

@Data
public class VehicleOrStarshipDTO {
    private String name;
    private String model;
    private String manufacturer;
    private String cost_in_credits;
    private String length;
    private String max_atmosphering_speed;
    private String crew;
    private String passengers;
    private String cargo_capacity;
    private String consumables;
    private List<String> pilots;
    private List<String> films;
    private String created;
    private String edited;
    private String url;
}