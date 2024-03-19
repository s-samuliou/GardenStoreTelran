package org.garden.com.repository;

import org.garden.com.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE (:categoryId IS NULL OR p.category.id = :categoryId)" +
            " AND (:minPrice IS NULL OR p.price >= :minPrice)" +
            " AND (:maxPrice IS NULL OR p.price <= :maxPrice)" +
            " AND (:discount IS NULL OR (p.discountPrice > 0 AND :discount = true) OR (:discount = false))" +
            " ORDER BY CASE WHEN :sort = 'asc' THEN p.price END ASC, CASE WHEN :sort = 'desc' THEN p.price END DESC")
    List<Product> findFilteredProducts(
            @Param("categoryId") Long categoryId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("discount") Boolean discount,
            @Param("sort") String sort);
}
