package com.diverger.swapi.extenal;

import com.diverger.swapi.domain.exception.PersonNotFoundException;
import com.diverger.swapi.infraestructure.entity.SwapiPeopleEntity;
import com.diverger.swapi.infraestructure.entity.SwapiPeopleResponseEntity;
import com.diverger.swapi.infraestructure.external.SwapiRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SwapiRepositoryImplTest {

    @Mock
    private RestTemplate restTemplate;

    private SwapiRepositoryImpl swapiRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        swapiRepository = new SwapiRepositoryImpl(restTemplate);
        swapiRepository.setBaseUrl("https://swapi.dev/api");
    }


    @Test
    void testGetPersonInfo_WhenPersonNotFound() {
        // Prepare the expected response for the restTemplate call
        SwapiPeopleResponseEntity mockResponse = new SwapiPeopleResponseEntity();
        mockResponse.setResults(new ArrayList<>().toArray(new SwapiPeopleEntity[0]));

        // Mock the restTemplate call to return the prepared response
        when(restTemplate.getForEntity(any(String.class), any(Class.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        // Execute the method under test and expect a PersonNotFoundException
        assertThrows(PersonNotFoundException.class, () -> {
            swapiRepository.getPersonInfo("NonExistentCharacter");
        }, "Expected a PersonNotFoundException but it was not thrown.");
    }
}
