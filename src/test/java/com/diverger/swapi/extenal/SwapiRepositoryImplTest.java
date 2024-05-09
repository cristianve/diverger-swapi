package com.diverger.swapi.extenal;

import com.diverger.swapi.infraestructure.external.SwapiRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

class SwapiRepositoryImplTest {

    @Mock
    private RestTemplate restTemplate;

    private SwapiRepositoryImpl swapiRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        swapiRepository = new SwapiRepositoryImpl(restTemplate);
    }

    @Test
    void testGetPersonInfo_WhenPersonFound() {
    }

    @Test
    void testGetPersonInfo_WhenPersonNotFound() {

    }
}