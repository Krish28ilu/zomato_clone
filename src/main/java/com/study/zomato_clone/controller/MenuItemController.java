package com.study.zomato_clone.controller;

import com.study.zomato_clone.dto.MenuItemRequestDTO;
import com.study.zomato_clone.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menuitem")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<String> addMenuItem(@RequestBody MenuItemRequestDTO menuItemRequestDTO){
        return menuItemService.addMenuItem(menuItemRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMenuItem(@PathVariable Long id, @RequestBody MenuItemRequestDTO menuItemRequestDTO){
        return menuItemService.updateMenuItem(menuItemRequestDTO, id);
    }
}
