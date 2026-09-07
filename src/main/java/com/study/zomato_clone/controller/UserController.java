package com.study.zomato_clone.controller;


import com.study.zomato_clone.dto.MenuItemResponseDTO;
import com.study.zomato_clone.dto.RestaurantResponseDTO;
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
    public List<UserResponseDTO> findAll() {
       return userService.findAll();
    }

    @GetMapping("/name")
    public ResponseEntity<List<UserResponseDTO>> findByName(@RequestParam String name) {
   return userService.findByName(name);
    }

    @PostMapping
    public UserResponseDTO addUser(@RequestBody UserRequestDTO userRequestDTO) {
       return userService.addUser(userRequestDTO);
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {

        return userService.updateUser(userRequestDTO, id);
    }

    @DeleteMapping("/{id}")
    public UserResponseDTO deleteUser(@PathVariable Long id) {
       return userService.deleteUser(id);
    }

    @GetMapping("/getNearbyRestaurants")
    public  List<RestaurantResponseDTO> getNearbyRestaurants(@RequestParam("lon") Double lon, @RequestParam ("lat") Double lat){
        return userService.getNearbyRestaurants(lon,lat);
    }
}
