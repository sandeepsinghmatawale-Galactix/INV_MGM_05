package com.barinventory.brands.entity;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "parent_company")
    private String parentCompany;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(name = "excise_code")
    private String exciseCode;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(
            mappedBy = "brand",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<BrandSize> sizes = new java.util.ArrayList<>();


    // ✅ VERY IMPORTANT METHODS

    public void addSize(BrandSize size) {
        sizes.add(size);
        size.setBrand(this);
    }

    public void removeSize(BrandSize size) {
        sizes.remove(size);
        size.setBrand(null);
    }

    public void clearSizes() {
        for (BrandSize size : sizes) {
            size.setBrand(null);
        }
        sizes.clear();
    }

    public enum Category {
        WHISKY, BEER, VODKA, RUM, WINE, GIN, BRANDY, TEQUILA, OTHER
    }
}