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




    @Query(value = "update Category set name = ?1 where id = ?2")
    Category update(String name, Long id);



    @Query(value = "select new com.library.dto.CategoryDto(c.id, c.name, count(p.category.id)) " +
            "from Category c left join Product p on c.id = p.category.id " +
            "group by c.id ")
    List<CategoryDto> getCategoriesAndSize();
}
