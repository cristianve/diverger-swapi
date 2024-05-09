package com.diverger.swapi.application.service;

import com.diverger.swapi.domain.PersonService;
import com.diverger.swapi.domain.model.PersonInfo;
import com.diverger.swapi.domain.repository.SwapiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final SwapiRepository swapiRepository;

    @Autowired
    public PersonServiceImpl(SwapiRepository swapiRepository) {
        this.swapiRepository = swapiRepository;
    }

    public PersonInfo getPersonInfo(String name) {
        return swapiRepository.getPersonInfo(name);
    }
}