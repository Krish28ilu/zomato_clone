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
    public RestaurantResponseDTO addRestaurant(@RequestBody RestaurantRequestDTO restaurantRequestDTO){
      return restaurantService.addRestaurant(restaurantRequestDTO);
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


    @DeleteMapping("/{id}")
    public RestaurantResponseDTO deleteRestaurant(@PathVariable Long id) {

         return   restaurantService.deleteRestaurant(id);



    }

    @PutMapping("/{id}")
    public RestaurantResponseDTO updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantRequestDTO restaurantRequestDTO) {

        return restaurantService.updateRestaurant(id, restaurantRequestDTO);


    }
}
