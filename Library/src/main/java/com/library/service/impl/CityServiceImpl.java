package com.library.service.impl;

import com.library.model.City;
import com.library.model.Country;
import com.library.repository.CityRepository;
import com.library.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public List<City> findAll() {

        return cityRepository.findAll();
    }

    @Override
    public Optional<City> findById(Long id) {
        return cityRepository.findById(id);
    }

    @Override
    public City save(City city) {
        City citySave = new City();
        citySave.setName(city.getName());
        citySave.setCountry(city.getCountry());  // Устанавливаем страну
        return cityRepository.save(citySave);
    }



    @Override
    public City update(City city) {
        City cityUpd = cityRepository.getReferenceById(city.getId());
        cityUpd.setName(city.getName());
        return cityRepository.save(cityUpd);
    }

    @Override
    public void deleteById(Long id) {
        cityRepository.deleteById(id);
    }


}
