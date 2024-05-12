package com.diverger.swapi.infraestructure.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SwapiPeopleResponseDTO {
    private SwapiPeopleDTO[] results;

    public SwapiPeopleDTO[] getResults() {
        return results;
    }


}
