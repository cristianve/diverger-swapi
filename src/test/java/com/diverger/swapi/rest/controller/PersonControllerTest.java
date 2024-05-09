package com.diverger.swapi.rest.controller;

import com.diverger.swapi.application.service.PersonServiceImpl;
import com.diverger.swapi.application.service.controller.PersonController;
import com.diverger.swapi.domain.exception.PersonNotFoundException;
import com.diverger.swapi.domain.model.PersonInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class PersonControllerTest {

    @Mock
    private PersonServiceImpl personService;

    private PersonController personController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        personController = new PersonController(personService);
    }

    @Test
    public void testGetPersonInfo() {
        String name = "Luke Skywalker";
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(name);

        when(personService.getPersonInfo(name)).thenReturn(personInfo);

        ResponseEntity<PersonInfo> responseEntity = personController.getPersonInfo(name);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(personInfo, responseEntity.getBody());
    }

    @Test
    public void testGetPersonInfo_NotFound() {
        String name = "Non-existent Name";

        when(personService.getPersonInfo(name)).thenThrow(new PersonNotFoundException());

        ResponseEntity<PersonInfo> responseEntity = personController.getPersonInfo(name);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}