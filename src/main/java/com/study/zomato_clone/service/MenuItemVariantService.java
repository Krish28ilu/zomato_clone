package com.study.zomato_clone.service;

import com.study.zomato_clone.dto.MenuItemVariantRequestDTO;
import com.study.zomato_clone.dto.MenuItemVariantResponseDTO;
import com.study.zomato_clone.entity.MenuItem;
import com.study.zomato_clone.entity.MenuItemVariant;
import com.study.zomato_clone.entity.Restaurant;
import com.study.zomato_clone.repository.MenuItemRepository;
import com.study.zomato_clone.repository.MenuItemVariantRepository;
import com.study.zomato_clone.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemVariantService {

    @Autowired
    private MenuItemVariantRepository menuItemVariantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public ResponseEntity<String> addMenuItemVariant(Long restaurantId, Long menuItemId,
            MenuItemVariantRequestDTO requestDTO) {

        if (restaurantId == null || restaurantId <= 0) {
            return new ResponseEntity<>(
                    "Restaurant id must be greater than 0",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (menuItemId == null || menuItemId <= 0) {
            return new ResponseEntity<>(
                    "Menu item id must be greater than 0",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (requestDTO == null) {
            return new ResponseEntity<>(
                    "Menu item variant request cannot be null",
                    HttpStatusCode.valueOf(400)
            );
        }

        Optional<Restaurant> restaurantOptional =
                restaurantRepository.findById(restaurantId);

        if (restaurantOptional.isEmpty()) {
            return new ResponseEntity<>(
                    "Restaurant id does not exist",
                    HttpStatusCode.valueOf(404)
            );
        }

        Optional<MenuItem> menuItemOptional =
                menuItemRepository.findById(menuItemId);

        if (menuItemOptional.isEmpty()) {
            return new ResponseEntity<>(
                    "Menu item id does not exist",
                    HttpStatusCode.valueOf(404)
            );
        }

        Restaurant restaurant = restaurantOptional.get();
        MenuItem menuItem = menuItemOptional.get();

        if (!menuItem.getRestaurant().getId().equals(restaurant.getId())) {
            return new ResponseEntity<>(
                    "Menu item does not belong to this restaurant",
                    HttpStatusCode.valueOf(400)
            );
        }

        MenuItemVariant menuItemVariant = new MenuItemVariant();

        menuItemVariant.setName(requestDTO.getDishVariantName());
        menuItemVariant.setPrice(requestDTO.getPrice());
        menuItemVariant.setAvailable(requestDTO.isAvailable());
        menuItemVariant.setInventoryManaged(requestDTO.isInventoryManaged());
        menuItemVariant.setCurrentAvailableInventoryCount(
                requestDTO.getCurrentAvailableInventoryCount()
        );

        menuItemVariant.setMenuItem(menuItem);


        menuItemVariantRepository.save(menuItemVariant);

        return new ResponseEntity<>(
                "Menu item variant added successfully",
                HttpStatusCode.valueOf(201)
        );
    }

    public ResponseEntity<String> updateMenuItemVariant(Long restaurantId, Long menuItemId, Long variantId,
            MenuItemVariantRequestDTO requestDTO) {

        if (restaurantId == null || restaurantId <= 0) {
            return new ResponseEntity<>(
                    "Restaurant id must be greater than 0",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (menuItemId == null || menuItemId <= 0) {
            return new ResponseEntity<>(
                    "Menu item id must be greater than 0",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (variantId == null || variantId <= 0) {
            return new ResponseEntity<>(
                    "Menu item variant id must be greater than 0",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (requestDTO == null) {
            return new ResponseEntity<>(
                    "Menu item variant request cannot be null",
                    HttpStatusCode.valueOf(400)
            );
        }

        Optional<Restaurant> restaurantOptional =
                restaurantRepository.findById(restaurantId);

        if (restaurantOptional.isEmpty()) {
            return new ResponseEntity<>(
                    "Restaurant id does not exist",
                    HttpStatusCode.valueOf(404)
            );
        }

        Optional<MenuItem> menuItemOptional =
                menuItemRepository.findById(menuItemId);

        if (menuItemOptional.isEmpty()) {
            return new ResponseEntity<>(
                    "Menu item id does not exist",
                    HttpStatusCode.valueOf(404)
            );
        }

        Optional<MenuItemVariant> variantOptional =
                menuItemVariantRepository.findById(variantId);

        if (variantOptional.isEmpty()) {
            return new ResponseEntity<>(
                    "Menu item variant id does not exist",
                    HttpStatusCode.valueOf(404)
            );
        }

        Restaurant restaurant = restaurantOptional.get();
        MenuItem menuItem = menuItemOptional.get();
        MenuItemVariant variant = variantOptional.get();

        if (!menuItem.getRestaurant().getId().equals(restaurant.getId())) {
            return new ResponseEntity<>(
                    "Menu item does not belong to this restaurant",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (!variant.getMenuItem().getId().equals(menuItem.getId())) {
            return new ResponseEntity<>(
                    "Menu item variant does not belong to this menu item",
                    HttpStatusCode.valueOf(400)
            );
        }

        variant.setName(requestDTO.getDishVariantName());
        variant.setPrice(requestDTO.getPrice());
        variant.setAvailable(requestDTO.isAvailable());
        variant.setInventoryManaged(requestDTO.isInventoryManaged());
        variant.setCurrentAvailableInventoryCount(
                requestDTO.getCurrentAvailableInventoryCount()
        );

        menuItemVariantRepository.save(variant);

        return new ResponseEntity<>(
                "Menu item variant updated successfully",
                HttpStatusCode.valueOf(200)
        );
    }

    public ResponseEntity<String> deleteMenuItemVariant(Long restaurantId, Long menuItemId, Long variantId) {

        if (restaurantId == null || restaurantId <= 0) {
            return new ResponseEntity<>(
                    "Restaurant id must be greater than 0",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (menuItemId == null || menuItemId <= 0) {
            return new ResponseEntity<>(
                    "Menu item id must be greater than 0",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (variantId == null || variantId <= 0) {
            return new ResponseEntity<>(
                    "Menu item variant id must be greater than 0",
                    HttpStatusCode.valueOf(400)
            );
        }

        Optional<Restaurant> restaurantOptional =
                restaurantRepository.findById(restaurantId);

        if (restaurantOptional.isEmpty()) {
            return new ResponseEntity<>(
                    "Restaurant id does not exist",
                    HttpStatusCode.valueOf(404)
            );
        }

        Optional<MenuItem> menuItemOptional =
                menuItemRepository.findById(menuItemId);

        if (menuItemOptional.isEmpty()) {
            return new ResponseEntity<>(
                    "Menu item id does not exist",
                    HttpStatusCode.valueOf(404)
            );
        }

        Optional<MenuItemVariant> variantOptional =
                menuItemVariantRepository.findById(variantId);

        if (variantOptional.isEmpty()) {
            return new ResponseEntity<>(
                    "Menu item variant id does not exist",
                    HttpStatusCode.valueOf(404)
            );
        }

        Restaurant restaurant = restaurantOptional.get();
        MenuItem menuItem = menuItemOptional.get();
        MenuItemVariant variant = variantOptional.get();

        if (!menuItem.getRestaurant().getId().equals(restaurant.getId())) {
            return new ResponseEntity<>(
                    "Menu item does not belong to this restaurant",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (!variant.getMenuItem().getId().equals(menuItem.getId())) {
            return new ResponseEntity<>(
                    "Menu item variant does not belong to this menu item",
                    HttpStatusCode.valueOf(400)
            );
        }

        menuItemVariantRepository.deleteById(variantId);

        return new ResponseEntity<>(
                "Menu item variant deleted successfully",
                HttpStatusCode.valueOf(200)
        );
    }

    public ResponseEntity<List<MenuItemVariantResponseDTO>> getAllMenuItemVariants(Long restaurantId, Long menuItemId) {

        if (restaurantId == null || restaurantId <= 0) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }

        if (menuItemId == null || menuItemId <= 0) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }

        Optional<Restaurant> restaurantOptional =
                restaurantRepository.findById(restaurantId);

        if (restaurantOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }

        Optional<MenuItem> menuItemOptional =
                menuItemRepository.findById(menuItemId);

        if (menuItemOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }

        Restaurant restaurant = restaurantOptional.get();
        MenuItem menuItem = menuItemOptional.get();

        if (!menuItem.getRestaurant().getId().equals(restaurant.getId())) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }

        List<MenuItemVariant> variants =
                menuItem.getMenuItemVariantList();

        List<MenuItemVariantResponseDTO> responseList =
                new ArrayList<>();

        for (MenuItemVariant variant : variants) {

            MenuItemVariantResponseDTO responseDTO =
                    new MenuItemVariantResponseDTO();

            responseDTO.setDishVariantName(variant.getName());
            responseDTO.setAvailable(variant.isAvailable());
            responseDTO.setPrice(variant.getPrice());

            responseList.add(responseDTO);
        }

        return new ResponseEntity<>(
                responseList,
                HttpStatusCode.valueOf(200)
        );
    }
}
