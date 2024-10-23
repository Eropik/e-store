package com.library.repository;

import com.library.dto.CategoryDto;
import com.library.model.Category;
import com.library.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select category from Category category where category.name =:name ")
    List<Category> findByName(String name);

  //Customer

    @Query("select new com.library.dto.CategoryDto(c.id, c.name, count(p.category.id)) from Category c inner join Product p on p.category.id = c.id group by c.id")
    List<CategoryDto> getCategoryAndProduct();
}