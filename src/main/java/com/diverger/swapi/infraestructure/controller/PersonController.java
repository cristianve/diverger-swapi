package com.diverger.swapi.infraestructure.controller;

import com.diverger.swapi.application.service.PersonServiceImpl;
import com.diverger.swapi.domain.exception.PersonNotFoundException;
import com.diverger.swapi.domain.model.PersonInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private final PersonServiceImpl personService;

    @Autowired
    public PersonController(PersonServiceImpl personService) {
        this.personService = personService;

    }

    @GetMapping("/swapi-proxy/person-info")
    public ResponseEntity<PersonInfo> getPersonInfo(@RequestParam("name") String name) {

        try {
            PersonInfo personInfo = personService.getPersonInfo(name);
            return new ResponseEntity<>(personInfo, HttpStatus.OK);
        } catch (PersonNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}