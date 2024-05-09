package com.diverger.swapi.domain.repository;


import com.diverger.swapi.domain.model.PersonInfo;

public interface SwapiRepository {
    PersonInfo getPersonInfo(String name);
}