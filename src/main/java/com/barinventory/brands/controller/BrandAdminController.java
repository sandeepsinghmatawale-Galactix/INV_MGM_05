package com.barinventory.brands.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.barinventory.brands.dtos.BrandDTO;
import com.barinventory.brands.dtos.BrandSizeDTO;
import com.barinventory.brands.entity.Brand;
import com.barinventory.brands.entity.BrandSize;
import com.barinventory.brands.service.BrandService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/brands")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class BrandAdminController {

    private final BrandService brandService;

    // ================= LIST =================
    @GetMapping
    public String list(Model model) {
        List<BrandDTO> brands = brandService.getAllActiveBrands();
        model.addAttribute("brands", brands);
        return "admin/brands/brand-list";
    }

    // ================= CREATE FORM =================
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("brand", new BrandDTO());
        model.addAttribute("categories", Brand.Category.values());
        return "admin/brands/brand-form";
    }

    // ================= SAVE =================
    @PostMapping("/new")
    public String save(@ModelAttribute BrandDTO dto) {
        brandService.createBrand(dto);
        return "redirect:/admin/brands";
    }

    // ================= EDIT FORM =================
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("brand", brandService.getBrandById(id));
        model.addAttribute("categories", Brand.Category.values());
        return "admin/brands/brand-form";
    }

    // ================= UPDATE =================
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @ModelAttribute BrandDTO dto) {
        brandService.updateBrand(id, dto);
        return "redirect:/admin/brands";
    }

    // ================= DELETE (SOFT DELETE) =================
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        brandService.deactivateBrand(id);
        return "redirect:/admin/brands";
    }

    // ================= MANAGE SIZES PAGE =================
    @GetMapping("/{id}/sizes")
    public String manageSizes(@PathVariable Long id, Model model) {

        model.addAttribute("brand", brandService.getBrandById(id));
        model.addAttribute("newSize", new BrandSizeDTO());
        model.addAttribute("packagings", BrandSize.Packaging.values());

        return "admin/brands/brand-sizes";
    }

    // ================= ADD SIZE =================
    @PostMapping("/{id}/sizes")
    public String addSize(@PathVariable Long id,
                          @ModelAttribute BrandSizeDTO dto) {

        brandService.addSizeToBrand(id, dto);
        return "redirect:/admin/brands/" + id + "/sizes";
    }

    // ================= DELETE SIZE =================
    @PostMapping("/{brandId}/sizes/{sizeId}/delete")
    public String deleteSize(@PathVariable Long brandId,
                             @PathVariable Long sizeId) {

        brandService.deactivateSize(sizeId);
        return "redirect:/admin/brands/" + brandId + "/sizes";
    }
}