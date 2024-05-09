package com.diverger.swapi.domain.ports.out;

import com.diverger.swapi.domain.model.PersonInfo;

public interface ExternalServiceSwapi {
    PersonInfo getPersonInfo(String name);
}
