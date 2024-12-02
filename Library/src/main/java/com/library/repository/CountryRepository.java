package com.library.repository;

import com.library.model.Category;
import com.library.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query(value = "update Country set name = ?1 where id = ?2")
    Country update(String name, Long id);


}
