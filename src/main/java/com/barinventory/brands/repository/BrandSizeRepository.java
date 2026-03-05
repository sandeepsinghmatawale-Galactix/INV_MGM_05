package com.barinventory.brands.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barinventory.brands.entity.BrandSize;

public interface BrandSizeRepository extends JpaRepository<BrandSize, Long> {

    // ── Active sizes for a brand ──────────────────────────────────────
    List<BrandSize> findByBrandIdAndActiveTrue(Long brandId);

    // ── Sorted for UI display ─────────────────────────────────────────
    List<BrandSize> findByBrandIdAndActiveTrueOrderByDisplayOrderAsc(Long brandId);

    // ── Soft-delete lookup ────────────────────────────────────────────
    Optional<BrandSize> findByIdAndActiveTrue(Long id);

    // ── Duplicate size label check per brand ──────────────────────────
    boolean existsByBrandIdAndSizeLabelIgnoreCase(Long brandId, String sizeLabel);
}