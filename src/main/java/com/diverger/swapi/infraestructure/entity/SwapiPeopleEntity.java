package com.diverger.swapi.infraestructure.entity;

import lombok.Data;

import java.util.List;

@Data
public class SwapiPeopleEntity {

    private String name;
    private String gender;
    private String birth_year;
    private String homeworld;
    private List<String> vehicles;
    private List<String> starships;
    private List<String> films;


}
