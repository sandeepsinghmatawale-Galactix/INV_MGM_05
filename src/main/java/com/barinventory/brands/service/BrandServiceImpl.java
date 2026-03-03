package com.barinventory.brands.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barinventory.brands.dtos.BrandDTO;
import com.barinventory.brands.dtos.BrandSizeDTO;
import com.barinventory.brands.entity.Brand;
import com.barinventory.brands.entity.BrandSize;
import com.barinventory.brands.repository.BrandRepository;
import com.barinventory.brands.repository.BrandSizeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandSizeRepository brandSizeRepository;

    @Override
    public BrandDTO createBrand(BrandDTO dto) {

        if (brandRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new RuntimeException("Brand already exists");
        }

        Brand brand = mapToEntity(dto);
        brandRepository.save(brand);

        return mapToDTO(brand);
    }

    @Override
    public BrandDTO updateBrand(Long id, BrandDTO dto) {

        Brand brand = brandRepository.findByIdWithSizes(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        brand.setName(dto.getName());
        brand.setParentCompany(dto.getParentCompany());
        brand.setCategory(dto.getCategory());
        brand.setExciseCode(dto.getExciseCode());

        return mapToDTO(brand);
    }

    @Override
    public void deactivateBrand(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        brand.setActive(false);
    }

    @Override
    @Transactional
    public void addSizeToBrand(Long brandId, BrandSizeDTO dto) {

        Brand brand = brandRepository.findByIdWithSizes(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        if (brandSizeRepository.existsByBrandIdAndSizeLabelIgnoreCase(
                brandId, dto.getSizeLabel())) {
            throw new RuntimeException("Size already exists for this brand");
        }

        BrandSize size = BrandSize.builder()
                .sizeLabel(dto.getSizeLabel())
                .price(dto.getPrice())
                .packaging(dto.getPackaging())
                .abvPercent(dto.getAbvPercent())
                .displayOrder(dto.getDisplayOrder())
                .active(true)
                .build();

        brand.addSize(size);
    }

    @Override
    public void deactivateSize(Long sizeId) {
        BrandSize size = brandSizeRepository.findByIdAndActiveTrue(sizeId)
                .orElseThrow(() -> new RuntimeException("Size not found"));
        size.setActive(false);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandDTO getBrandById(Long id) {
        return brandRepository.findByIdWithSizes(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BrandDTO> getAllActiveBrands() {
        return brandRepository.findAllActiveWithSizes()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ================== MAPPERS ==================

    private Brand mapToEntity(BrandDTO dto) {

        Brand brand = Brand.builder()
                .name(dto.getName())
                .parentCompany(dto.getParentCompany())
                .category(dto.getCategory())
                .exciseCode(dto.getExciseCode())
                .active(true)
                .build();

        return brand;
    }

    private BrandDTO mapToDTO(Brand brand) {
        return BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .parentCompany(brand.getParentCompany())
                .category(brand.getCategory())
                .exciseCode(brand.getExciseCode())
                .active(brand.isActive())
                .sizes(
                        brand.getSizes().stream()
                                .filter(BrandSize::isActive)
                                .map(s -> BrandSizeDTO.builder()
                                        .id(s.getId())
                                        .sizeLabel(s.getSizeLabel())
                                        .price(s.getPrice())
                                        .packaging(s.getPackaging())
                                        .abvPercent(s.getAbvPercent())
                                        .displayOrder(s.getDisplayOrder())
                                        .active(s.isActive())
                                        .build())
                                .toList()
                )
                .build();
    }
}