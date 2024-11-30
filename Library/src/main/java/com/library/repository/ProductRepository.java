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

    @Query("select p from Product p ")
    List<Product> getAllProduct();

    @Query("select p from Product p where p.name like %?1% or p.description like %?1%")
    List<Product> findAllByNameOrDescription(String keyword);


    @Query("select p from Product p inner join Category c ON c.id = p.category.id" +
            " where p.category.name = ?1 ")
    List<Product> findAllByCategory(String category);

    @Query(value = "select " +
            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image " +
            "from products p order by random() limit 9", nativeQuery = true)
    List<Product> randomProduct();

    @Query(value = "select " +
            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image " +
            "from products p order by p.cost_price desc limit 9", nativeQuery = true)
    List<Product> filterHighProducts();

    @Query(value = "select " +
            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image " +
            "from products p order by p.cost_price asc limit 9", nativeQuery = true)
    List<Product> filterLowerProducts();


    @Query(value = "select p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image from products p  limit 4", nativeQuery = true)
    List<Product> listViewProduct();


    @Query(value = "select p from Product p inner join Category c on c.id = ?1 and p.category.id = ?1")
    List<Product> getProductByCategoryId(Long id);


    @Query("select p from Product p where p.name like %?1% or p.description like %?1%")
    List<Product> searchProducts(String keyword);

}
