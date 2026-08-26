package com.study.zomato_clone.controller;

import com.study.zomato_clone.dto.MenuItemRequestDTO;
import com.study.zomato_clone.service.MenuItemVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menuItemVariant")
public class MenuItemVariantController {


    @Autowired
    private MenuItemVariantService menuItemVariantService;

    @PutMapping("/{id}")
    public ResponseEntity<String> editMenuItemVariant(@PathVariable Long id, @RequestBody MenuItemRequestDTO menuItemRequestDTO){
        return null;
    }
}
