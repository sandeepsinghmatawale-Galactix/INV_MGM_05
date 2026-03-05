package com.barinventory.brands.dtos;

import java.math.BigDecimal;
import java.util.List;
import com.barinventory.brands.entity.Brand;
import lombok.*;

// ── BrandDTO ──────────────────────────────────────────────────────────────────
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BrandDTO {
    private Long id;
    private String brandCode;
    private String brandName;
    private String parentCompany;
    private Brand.Category category;
    private Brand.SubCategory subCategory;
    private String exciseCode;
    private BigDecimal exciseCessPercent;
    private BigDecimal tcsPercent;
    private BigDecimal gstPercent;
    private boolean active;
    private List<BrandSizeDTO> sizes;
}