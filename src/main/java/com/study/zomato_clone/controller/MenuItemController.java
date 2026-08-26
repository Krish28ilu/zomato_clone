package com.study.zomato_clone.controller;

import com.study.zomato_clone.dto.MenuItemRequestDTO;
import com.study.zomato_clone.dto.MenuItemResponseDTO;
import com.study.zomato_clone.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menuitem")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponseDTO> getMenuItem(@PathVariable Long id) {
        return menuItemService.getMenuItem(id);
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDTO>> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    @PostMapping
    public ResponseEntity<String> addMenuItem(@RequestBody MenuItemRequestDTO menuItemRequestDTO){
        return menuItemService.addMenuItem(menuItemRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMenuItem(@PathVariable Long id, @RequestBody MenuItemRequestDTO menuItemRequestDTO){
        return menuItemService.updateMenuItem(menuItemRequestDTO, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenuItem( @PathVariable Long id){

        return menuItemService.deleteMenuItem(id);
    }


}
