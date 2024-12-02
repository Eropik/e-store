package com.library.service;

import com.library.model.City;
import com.library.model.Country;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CityService {

    List<City> findAll();

    Optional<City> findById(Long id);

    City save(City city);

    City update(City city);

    void deleteById(Long id);

}
