package com.library.repository;

import com.library.model.City;
import com.library.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query(value = "update City set name = ?1 where id = ?2")
    City update(String name, Long id);
}
