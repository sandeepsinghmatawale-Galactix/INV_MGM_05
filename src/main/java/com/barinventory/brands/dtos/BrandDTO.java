package com.barinventory.brands.dtos;

import java.util.List;

import com.barinventory.brands.entity.Brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
	private Long id;
	private String name;
	private String parentCompany;
	private Brand.Category category;
	private String exciseCode;
	private boolean active;
	private List<BrandSizeDTO> sizes;
}