package com.diverger.swapi.infraestructure.adapters;

import com.diverger.swapi.domain.exception.PersonNotFoundException;
import com.diverger.swapi.domain.model.Film;
import com.diverger.swapi.domain.model.PersonInfo;
import com.diverger.swapi.domain.ports.out.ExternalServiceSwapi;
import com.diverger.swapi.infraestructure.dto.PlanetDTO;
import com.diverger.swapi.infraestructure.dto.SwapiPeopleDTO;
import com.diverger.swapi.infraestructure.dto.SwapiPeopleResponseDTO;
import com.diverger.swapi.infraestructure.dto.VehicleOrStarshipDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class ExternalServiceAdapter implements ExternalServiceSwapi {

    private final RestTemplate restTemplate;

    @Value("${swapi.base-url}")
    private String baseUrl;

    private final String peoplePath = "/people";

    public ExternalServiceAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public PersonInfo getPersonInfo(String name) {
        String url = baseUrl + peoplePath;
        ResponseEntity<SwapiPeopleResponseDTO> response = restTemplate.getForEntity(url, SwapiPeopleResponseDTO.class);

        Optional<PersonInfo> personInfo = response.getStatusCode() == HttpStatus.OK && response.getBody() != null ?
                findPersonInfoByName(response.getBody(), name) :
                Optional.empty();

        return personInfo.orElseThrow(() -> new PersonNotFoundException());
    }

    private Optional<PersonInfo> findPersonInfoByName(SwapiPeopleResponseDTO swapiPeopleResponse, String name) {
        for (SwapiPeopleDTO person : swapiPeopleResponse.getResults()) {
            if (person.getName().equalsIgnoreCase(name)) {
                return Optional.of(mapToPersonInfo(person));
            }
        }
        return Optional.empty();
    }

    private PersonInfo mapToPersonInfo(SwapiPeopleDTO person) {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(person.getName());
        personInfo.setBirth_year(person.getBirth_year());
        personInfo.setGender(person.getGender());
        personInfo.setPlanet_name(this.extractPlanetName(person.getHomeworld()));
        personInfo.setFastest_vehicle_driven(this.extractFastestVehicleOrStarshipName(mergeVehicleAndStarshipUrls(person.getVehicles(), person.getStarships())));
        personInfo.setFilms(this.extractFilms(person.getFilms()));
        return personInfo;
    }

    private String extractPlanetName(String homeworldUrl) {


        ResponseEntity<PlanetDTO> response = restTemplate.getForEntity(baseUrl + homeworldUrl.replace(baseUrl, ""), PlanetDTO.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getName();
        }

        return "";


    }

    private String extractFastestVehicleOrStarshipName(List<String> vehicleOrStarshipUrls) {
        int maxSpeed = 0;
        String fastestVehicleOrStarshipName = "None";

        for (String url : vehicleOrStarshipUrls) {
            ResponseEntity<VehicleOrStarshipDTO> response = restTemplate.getForEntity(baseUrl + url.replace(baseUrl, ""), VehicleOrStarshipDTO.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                VehicleOrStarshipDTO vehicleOrStarship = response.getBody();
                int speed = Integer.parseInt(vehicleOrStarship.getMax_atmosphering_speed());
                if (speed > maxSpeed) {
                    maxSpeed = speed;
                    fastestVehicleOrStarshipName = vehicleOrStarship.getName();
                }
            }
        }

        return maxSpeed > 0 ? fastestVehicleOrStarshipName : "None";
    }

    private List<Film> extractFilms(List<String> filmUrls) {

        return filmUrls.stream()
                .map(url -> restTemplate.getForEntity(baseUrl + url.replace(baseUrl, ""), Film.class))
                .filter(response -> response.getStatusCode() == HttpStatus.OK && response.getBody() != null)
                .map(ResponseEntity::getBody)
                .collect(Collectors.toList());
    }

    private List<String> mergeVehicleAndStarshipUrls(List<String> vehicleUrls, List<String> starshipUrls) {
        List<String> mergedUrls = new ArrayList<>();
        if (vehicleUrls != null) {
            mergedUrls.addAll(vehicleUrls);
        }
        if (starshipUrls != null) {
            mergedUrls.addAll(starshipUrls);
        }
        return mergedUrls;
    }
}
