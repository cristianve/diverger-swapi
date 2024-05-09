package com.diverger.swapi.application.service;

import com.diverger.swapi.domain.model.PersonInfo;
import com.diverger.swapi.domain.ports.in.RetrievePersonInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements RetrievePersonInfo {

    private final RetrievePersonInfo retrievePersonInfo;

    @Autowired
    public PersonServiceImpl(RetrievePersonInfo retrievePersonInfo) {
        this.retrievePersonInfo = retrievePersonInfo;
    }


    @Override
    public PersonInfo getPersonInfo(String name) {
        return retrievePersonInfo.getPersonInfo(name);
    }
}