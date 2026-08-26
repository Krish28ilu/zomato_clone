package com.study.zomato_clone.controller;

import com.study.zomato_clone.dto.RestaurantRequestDTO;
import com.study.zomato_clone.dto.RestaurantResponseDTO;
import com.study.zomato_clone.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;


    @PostMapping("/addRestaurant")
    public ResponseEntity<String> addRestaurant(@RequestBody RestaurantRequestDTO restaurantRequestDTO){
        restaurantService.addRestaurant(restaurantRequestDTO);
        return new ResponseEntity<>(
                "Restaurant added successfully",
                HttpStatusCode.valueOf(201)
        );
    }


//    @GetMapping
//    public ResponseEntity<String> getRestaurant(@RequestParam("name") String name){
//
//        return null;
//    }

    @GetMapping("/{id}")
    public RestaurantResponseDTO getRestaurant(@PathVariable Long id){

        return restaurantService.getRestaurant(id);
    }
}
