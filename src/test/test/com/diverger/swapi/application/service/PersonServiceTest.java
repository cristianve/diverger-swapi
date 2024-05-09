package com.diverger.swapi.application.service;

import com.diverger.swapi.application.service.PersonServiceImpl;
import com.diverger.swapi.domain.model.PersonInfo;
import com.diverger.swapi.domain.ports.in.RetrievePersonInfo;
import com.diverger.swapi.infraestructure.adapters.ExternalServiceAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class PersonServiceTest {

    @Mock
    private RetrievePersonInfo retrievePersonInfo;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPersonInfo() {
        String name = "Luke Skywalker";
        PersonInfo expectedPersonInfo = new PersonInfo();

        when(retrievePersonInfo.getPersonInfo(anyString())).thenReturn(expectedPersonInfo);


        PersonInfo personInfo = personService.getPersonInfo(name);


        assertEquals(expectedPersonInfo, personInfo);
    }
}