package com.library.service;

import com.library.model.Country;

import java.util.List;
import java.util.Optional;


public interface CountryService {
    List<Country> findAll();

    Optional<Country> findById(Long id);

    Country save(Country country);

    Country update(Country country);

    void deleteById(Long id);
}
