package com.barinventory.brands.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brands")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ── REQUIRED ──────────────────────────────────────────────
    @Column(name = "brand_code", nullable = false, unique = true, length = 20)
    private String brandCode;

    @Column(name = "brand_name", nullable = false, unique = true)
    private String brandName;

    // ── OPTIONAL ──────────────────────────────────────────────
    @Column(name = "parent_company")
    private String parentCompany;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")                  // nullable — user may skip
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "sub_category")
    private SubCategory subCategory;

    @Column(name = "excise_code", length = 50)
    private String exciseCode;

    @Column(name = "excise_cess_percent", precision = 5, scale = 2)
    private BigDecimal exciseCessPercent;

    @Column(name = "tcs_percent", precision = 5, scale = 2)
    private BigDecimal tcsPercent;

    @Column(name = "gst_percent", precision = 5, scale = 2)
    private BigDecimal gstPercent;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    // ── SIZES ─────────────────────────────────────────────────
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BrandSize> sizes = new ArrayList<>();

    public void addSize(BrandSize size)    { sizes.add(size); size.setBrand(this); }
    public void removeSize(BrandSize size) { sizes.remove(size); size.setBrand(null); }
    public void clearSizes() {
        sizes.forEach(s -> s.setBrand(null));
        sizes.clear();
    }

    // ── ENUMS ─────────────────────────────────────────────────
    public enum Category {
        WHISKY, BEER, VODKA, RUM, WINE, GIN, BRANDY, TEQUILA, OTHER
    }

    public enum SubCategory {
        INDIAN_MADE_FOREIGN_LIQUOR, COUNTRY_LIQUOR, IMPORTED, CRAFT, OTHER
    }
}