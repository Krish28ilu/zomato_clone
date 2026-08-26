package com.study.zomato_clone.controller;


import com.study.zomato_clone.dto.MenuItemResponseDTO;
import com.study.zomato_clone.dto.UserRequestDTO;
import com.study.zomato_clone.dto.UserResponseDTO;
import com.study.zomato_clone.service.MenuItemService;
import com.study.zomato_clone.service.RestaurantService;
import com.study.zomato_clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;


    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
       return userService.findAll();
    }

    @GetMapping("/name")
    public ResponseEntity<List<UserResponseDTO>> findByName(@RequestParam String name) {
   return userService.findByName(name);
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserRequestDTO userRequestDTO) {
       return userService.addUser(userRequestDTO);
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(userRequestDTO);
    }

    @DeleteMapping("/{id{")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
       return userService.deleteUser(id);
    }

}
