package com.study.zomato_clone.controller;

import com.study.zomato_clone.dto.MenuItemRequestDTO;
import com.study.zomato_clone.dto.MenuItemResponseDTO;
import com.study.zomato_clone.dto.MenuItemVariantRequestDTO;
import com.study.zomato_clone.dto.MenuItemVariantResponseDTO;
import com.study.zomato_clone.service.MenuItemVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menuItemVariant")
public class MenuItemVariantController {


    @Autowired
    private MenuItemVariantService menuItemVariantService;


    @PostMapping("/restaurant/{restaurantId}/menuitem/{menuItemId}")
    public ResponseEntity<String> addMenuItemVariant(
            @PathVariable Long restaurantId,
            @PathVariable Long menuItemId,
            @RequestBody MenuItemVariantRequestDTO requestDTO) {

        return menuItemVariantService.addMenuItemVariant(
                restaurantId,
                menuItemId,
                requestDTO
        );
    }

    @GetMapping("/restaurant/{restaurantId}/menuitem/{menuItemId}")
    public ResponseEntity<List<MenuItemVariantResponseDTO>> getAllMenuItemVariants(
            @PathVariable Long restaurantId,
            @PathVariable Long menuItemId) {

        return menuItemVariantService.getAllMenuItemVariants(
                restaurantId,
                menuItemId
        );
    }

    @PutMapping("/restaurant/{restaurantId}/menuitem/{menuItemId}/variant/{variantId}")
    public ResponseEntity<String> updateMenuItemVariant(
            @PathVariable Long restaurantId,
            @PathVariable Long menuItemId,
            @PathVariable Long variantId,
            @RequestBody MenuItemVariantRequestDTO requestDTO) {

        return menuItemVariantService.updateMenuItemVariant(
                restaurantId,
                menuItemId,
                variantId,
                requestDTO
        );
    }

    @DeleteMapping("/restaurant/{restaurantId}/menuitem/{menuItemId}/variant/{variantId}")
    public ResponseEntity<String> deleteMenuItemVariant(
            @PathVariable Long restaurantId,
            @PathVariable Long menuItemId,
            @PathVariable Long variantId) {

        return menuItemVariantService.deleteMenuItemVariant(
                restaurantId,
                menuItemId,
                variantId
        );
    }
}
