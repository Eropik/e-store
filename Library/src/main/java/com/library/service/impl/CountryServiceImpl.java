package com.library.service.impl;

import com.library.model.Category;
import com.library.model.Country;
import com.library.repository.CountryRepository;
import com.library.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }


    @Override
    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }


    @Override
    public Country save(Country country) {
       Country countrySave = new Country();
       countrySave.setName(country.getName());
        return countryRepository.save(countrySave);
    }

    @Override
    public Country update(Country country) {
        Country countryUpd = countryRepository.getReferenceById(country.getId());
        countryUpd.setName(country.getName());
        return countryRepository.save(countryUpd);
    }

    @Override
    public void deleteById(Long id) {
        countryRepository.deleteById(id);
    }
}
