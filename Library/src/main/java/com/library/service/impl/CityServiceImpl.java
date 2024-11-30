package com.library.service.impl;

import com.library.model.City;
import com.library.repository.CityRepository;
import com.library.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public List<City> findAll() {

        return cityRepository.findAll();
    }


}
