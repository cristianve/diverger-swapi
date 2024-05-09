package com.diverger.swapi.domain.ports.in;

import com.diverger.swapi.domain.model.PersonInfo;

public interface RetrievePersonInfo {

    PersonInfo getPersonInfo(String name);

}
