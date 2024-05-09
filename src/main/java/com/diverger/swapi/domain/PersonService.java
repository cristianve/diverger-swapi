package com.diverger.swapi.domain;

import com.diverger.swapi.domain.model.PersonInfo;

public interface PersonService {

    PersonInfo getPersonInfo(String name);
}
