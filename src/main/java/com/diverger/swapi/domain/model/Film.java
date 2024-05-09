package com.diverger.swapi.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Film {

    @JsonProperty("title")
    private String name;
    private String release_date;


}