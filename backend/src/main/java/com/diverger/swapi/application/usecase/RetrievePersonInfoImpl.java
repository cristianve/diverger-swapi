package com.diverger.swapi.application.usecase;

import com.diverger.swapi.domain.model.PersonInfo;
import com.diverger.swapi.domain.ports.in.RetrievePersonInfo;
import com.diverger.swapi.infraestructure.adapters.ExternalServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetrievePersonInfoImpl implements RetrievePersonInfo {

    private final ExternalServiceAdapter externalServiceAdapter;

    @Autowired
    public RetrievePersonInfoImpl(ExternalServiceAdapter swapiRepository) {
        this.externalServiceAdapter = swapiRepository;
    }


    @Override
    public PersonInfo getPersonInfo(String name) {
        return externalServiceAdapter.getPersonInfo(name);
    }
}