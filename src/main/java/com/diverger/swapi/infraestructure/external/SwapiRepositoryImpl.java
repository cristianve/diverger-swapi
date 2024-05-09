package com.diverger.swapi.infraestructure.external;

import com.diverger.swapi.domain.exception.PersonNotFoundException;
import com.diverger.swapi.domain.model.Film;
import com.diverger.swapi.domain.model.PersonInfo;
import com.diverger.swapi.domain.repository.SwapiRepository;
import com.diverger.swapi.infraestructure.entity.PlanetEntity;
import com.diverger.swapi.infraestructure.entity.SwapiPeopleEntity;
import com.diverger.swapi.infraestructure.entity.SwapiPeopleResponseEntity;
import com.diverger.swapi.infraestructure.entity.VehicleOrStarshipEntity;
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
public class SwapiRepositoryImpl implements SwapiRepository {

    private final RestTemplate restTemplate;

    @Value("${swapi.base-url}")
    private String baseUrl;

    private final String peoplePath = "/people";

    public SwapiRepositoryImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public PersonInfo getPersonInfo(String name) {
        String url = baseUrl + peoplePath;
        ResponseEntity<SwapiPeopleResponseEntity> response = restTemplate.getForEntity(url, SwapiPeopleResponseEntity.class);

        Optional<PersonInfo> personInfo = response.getStatusCode() == HttpStatus.OK && response.getBody() != null ?
                findPersonInfoByName(response.getBody(), name) :
                Optional.empty();

        return personInfo.orElseThrow(() -> new PersonNotFoundException());
    }

    private Optional<PersonInfo> findPersonInfoByName(SwapiPeopleResponseEntity swapiPeopleResponse, String name) {
        for (SwapiPeopleEntity person : swapiPeopleResponse.getResults()) {
            if (person.getName().equalsIgnoreCase(name)) {
                return Optional.of(mapToPersonInfo(person));
            }
        }
        return Optional.empty();
    }

    private PersonInfo mapToPersonInfo(SwapiPeopleEntity person) {
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


        ResponseEntity<PlanetEntity> response = restTemplate.getForEntity(baseUrl + homeworldUrl.replace(baseUrl, ""), PlanetEntity.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getName();
        }

        return "";


    }

    private String extractFastestVehicleOrStarshipName(List<String> vehicleOrStarshipUrls) {
        int maxSpeed = 0;
        String fastestVehicleOrStarshipName = "None";

        for (String url : vehicleOrStarshipUrls) {
            ResponseEntity<VehicleOrStarshipEntity> response = restTemplate.getForEntity(baseUrl + url.replace(baseUrl, ""), VehicleOrStarshipEntity.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                VehicleOrStarshipEntity vehicleOrStarship = response.getBody();
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
