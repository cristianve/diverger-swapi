package com.diverger.swapi.infraestructure.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SwapiPeopleResponseEntity {
    private SwapiPeopleEntity[] results;

    public SwapiPeopleEntity[] getResults() {
        return results;
    }


}
