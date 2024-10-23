package com.library.repository;

import com.library.model.Category;
import com.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //Admin
    @Query("select p from Product p")
    Page<Product> pageProduct(Pageable pageable);


    @Query("select p from Product p where p.description like %?1% or p.name like %?1% ")
    Page<Product> searchProducts(Pageable pageable, String keyword);

    @Query("select p from Product p where p.description like %?1% or p.name like %?1% ")
    List<Product> searchProductList(String keyword);


    Product findByName(String name);

    //Customer

    @Query("select p from Product p")
    List<Product> getAllProduct();

    @Query(value = "select p from products p order by rand() asc limit 4", nativeQuery = true)
    List<Product> listViewProducts();

    @Query(value = "select * from products p inner join categories c on c.category_id= p.category_id where p.category_id = ?1 ", nativeQuery = true)
    List<Product> findProductByCategoryId(Long categoryId);

    @Query(value = "select p from Product p inner join Category c on c.id = p.category.id where c.id = ?1", nativeQuery = true)
    List<Product> getProductsInCategory(Long categoryId);

    @Query("select p from Product p order by p.costPrice desc ")
    List<Product> filterHighToLowPrice();

    @Query("select p from Product p order by p.costPrice  ")
    List<Product> filterLowToHighPrice();


}
