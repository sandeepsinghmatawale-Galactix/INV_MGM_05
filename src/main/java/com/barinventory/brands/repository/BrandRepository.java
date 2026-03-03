package com.barinventory.brands.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.barinventory.brands.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    boolean existsByNameIgnoreCase(String name);

    List<Brand> findByActiveTrue();

    @Query("""
           SELECT DISTINCT b FROM Brand b
           LEFT JOIN FETCH b.sizes s
           WHERE b.id = :id
           """)
    Optional<Brand> findByIdWithSizes(@Param("id") Long id);

    @Query("""
           SELECT DISTINCT b FROM Brand b
           LEFT JOIN FETCH b.sizes s
           WHERE b.active = true AND (s.active = true OR s IS NULL)
           ORDER BY b.name
           """)
    List<Brand> findAllActiveWithSizes();
    
    List<Brand> findByCategoryAndActiveTrue(Brand.Category category);
}