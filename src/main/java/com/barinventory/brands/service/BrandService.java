package com.barinventory.brands.service;

import java.util.List;

import com.barinventory.brands.dtos.BrandDTO;
import com.barinventory.brands.dtos.BrandSizeDTO;



public interface BrandService {
	
	  BrandDTO createBrand(BrandDTO dto);

	    BrandDTO updateBrand(Long id, BrandDTO dto);

	    void deactivateBrand(Long id);

	    BrandDTO getBrandById(Long id);

	    List<BrandDTO> getAllActiveBrands();

	    void addSizeToBrand(Long brandId, BrandSizeDTO dto);

	    void deactivateSize(Long sizeId);

}
