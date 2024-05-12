package com.diverger.swapi.infraestructure.adapters;

import com.diverger.swapi.domain.exception.PersonNotFoundException;
import com.diverger.swapi.infraestructure.dto.SwapiPeopleDTO;
import com.diverger.swapi.infraestructure.dto.SwapiPeopleResponseDTO;
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

    private ExternalServiceAdapter externalServiceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        externalServiceAdapter = new ExternalServiceAdapter(restTemplate);
        externalServiceAdapter.setBaseUrl("https://swapi.dev/api");
    }


    @Test
    void testGetPersonInfo_WhenPersonNotFound() {
        // Prepare the expected response for the restTemplate call
        SwapiPeopleResponseDTO mockResponse = new SwapiPeopleResponseDTO();
        mockResponse.setResults(new ArrayList<>().toArray(new SwapiPeopleDTO[0]));

        // Mock the restTemplate call to return the prepared response
        when(restTemplate.getForEntity(any(String.class), any(Class.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        // Execute the method under test and expect a PersonNotFoundException
        assertThrows(PersonNotFoundException.class, () -> {
            externalServiceAdapter.getPersonInfo("NonExistentCharacter");
        }, "Expected a PersonNotFoundException but it was not thrown.");
    }
}
