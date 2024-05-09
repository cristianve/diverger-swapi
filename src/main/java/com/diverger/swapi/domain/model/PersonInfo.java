package com.diverger.swapi.domain.model;

import lombok.*;

import java.util.List;


@Data
public class PersonInfo {
    private String name;
    private String birth_year;
    private String gender;
    private String planet_name;
    private String fastest_vehicle_driven;
    private List<Film> films;
}
