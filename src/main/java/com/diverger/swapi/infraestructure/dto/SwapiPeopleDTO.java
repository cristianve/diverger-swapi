package com.diverger.swapi.infraestructure.dto;

import lombok.Data;

import java.util.List;

@Data
public class SwapiPeopleDTO {

    private String name;
    private String gender;
    private String birth_year;
    private String homeworld;
    private List<String> vehicles;
    private List<String> starships;
    private List<String> films;


}
