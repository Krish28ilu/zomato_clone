package com.study.zomato_clone.service;


import com.study.zomato_clone.dto.MenuItemRequestDTO;
import com.study.zomato_clone.dto.MenuItemResponseDTO;
import com.study.zomato_clone.dto.MenuItemVariantRequestDTO;
import com.study.zomato_clone.dto.MenuItemVariantResponseDTO;
import com.study.zomato_clone.entity.MenuItem;
import com.study.zomato_clone.entity.MenuItemVariant;
import com.study.zomato_clone.entity.Restaurant;
import com.study.zomato_clone.repository.MenuItemRepository;
import com.study.zomato_clone.repository.RestaurantRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public ResponseEntity<String> addMenuItem(MenuItemRequestDTO menuItemRequestDTO) {

        if (menuItemRequestDTO == null) {
            return new ResponseEntity<>(
                    "Menu item request cannot be null", HttpStatusCode.valueOf(400));
        }

        if (menuItemRequestDTO.getRestaurantId() == null) {
            return new ResponseEntity<>(
                    "Restaurant id cannot be null", HttpStatusCode.valueOf(400));
        }

        if (menuItemRequestDTO.getRestaurantId() <= 0) {
            return new ResponseEntity<>(
                    "Restaurant id must be greater than 0", HttpStatusCode.valueOf(400));
        }


        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(menuItemRequestDTO.getRestaurantId());

        if(restaurantOptional.isEmpty()){
            return new ResponseEntity<>("Rest. id does not exist", HttpStatusCode.valueOf(400));
        }

         Restaurant restaurant = restaurantOptional.get();

        MenuItem existingMenuItem = menuItemRepository.findByRestaurantAndName(restaurant,menuItemRequestDTO.getName());

        if(existingMenuItem != null){
            return new ResponseEntity<>("This menu item already added", HttpStatusCode.valueOf(400));
        }

        if (StringUtils.isBlank(menuItemRequestDTO.getName())) {
            return new ResponseEntity<>(
                    "Menu item name is required",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (menuItemRequestDTO.getName().length() > 100) {
            return new ResponseEntity<>(
                    "Menu item name cannot exceed 100 characters",
                    HttpStatusCode.valueOf(400)
            );
        }


        if (StringUtils.isBlank(menuItemRequestDTO.getDescription())) {
            return new ResponseEntity<>(
                    "Description is required",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (menuItemRequestDTO.getDescription().length() > 1000) {
            return new ResponseEntity<>(
                    "Description cannot exceed 1000 characters",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (menuItemRequestDTO.getMenuItemType() == null) {
            return new ResponseEntity<>(
                    "Menu item type is required",
                    HttpStatusCode.valueOf(400)
            );
        }




        MenuItem menuItem = convertMenuItemRequestDTOToMenuItem(menuItemRequestDTO,restaurant);

        menuItemRepository.save(menuItem);

        return new ResponseEntity<>("Menu item added success", HttpStatusCode.valueOf(201));


    }

    private MenuItem convertMenuItemRequestDTOToMenuItem(MenuItemRequestDTO menuItemRequestDTO,Restaurant restaurant) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequestDTO.getName());
        menuItem.setLabel(menuItemRequestDTO.getLabel());
        menuItem.setDescription(menuItemRequestDTO.getDescription());
        menuItem.setMenuItemType(menuItemRequestDTO.getMenuItemType());
        menuItem.setRestaurant(restaurant);
        List<MenuItemVariant> menuItemVariantList = new ArrayList<>();

        List<MenuItemVariantRequestDTO> menuItemVariantRequestDTOS = menuItemRequestDTO.getMenuItemVariants();

        for(MenuItemVariantRequestDTO menuItemVariantRequestDTO : menuItemVariantRequestDTOS){
            MenuItemVariant menuItemVariant = new MenuItemVariant();

            menuItemVariant.setName(menuItemVariantRequestDTO.getDishVariantName());
            menuItemVariant.setPrice(menuItemVariantRequestDTO.getPrice());
            menuItemVariant.setInventoryManaged(menuItemVariantRequestDTO.isInventoryManaged());
            menuItemVariant.setAvailable(menuItemVariantRequestDTO.isAvailable());
            menuItemVariant.setCurrentAvailableInventoryCount(menuItemVariantRequestDTO.getCurrentAvailableInventoryCount());
            menuItemVariant.setMenuItem(menuItem);
            menuItemVariantList.add(menuItemVariant);
        }
        menuItem.setMenuItemVariantList(menuItemVariantList);


        return menuItem;
    }


    public ResponseEntity<String> updateMenuItem(MenuItemRequestDTO menuItemRequestDTO,Long id) {

        if (menuItemRequestDTO == null) {
            return new ResponseEntity<>("Menu item request cannot be null",
                    HttpStatusCode.valueOf(400));
        }

        if (id == null || id <= 0) {
            return new ResponseEntity<>("Menu item id must be greater than 0",
                    HttpStatusCode.valueOf(400));
        }

        Optional<MenuItem> menuItemOptional = menuItemRepository.findById(id);

        if (menuItemOptional.isEmpty()) {
            return new ResponseEntity<>("Menu item id does not exist",
                    HttpStatusCode.valueOf(404));
        }

        if (menuItemRequestDTO.getRestaurantId() == null) {
            return new ResponseEntity<>("Restaurant id cannot be null",
                    HttpStatusCode.valueOf(400));
        }

        Optional<Restaurant> restaurantOptional =
                restaurantRepository.findById(menuItemRequestDTO.getRestaurantId());

        if (restaurantOptional.isEmpty()) {
            return new ResponseEntity<>("Restaurant id does not exist",
                    HttpStatusCode.valueOf(400));
        }

        MenuItem menuItem = menuItemOptional.get();
        Restaurant restaurant = restaurantOptional.get();

        menuItem.setName(menuItemRequestDTO.getName());
        menuItem.setLabel(menuItemRequestDTO.getLabel());
        menuItem.setDescription(menuItemRequestDTO.getDescription());
        menuItem.setMenuItemType(menuItemRequestDTO.getMenuItemType());
        menuItem.setRestaurant(restaurant);

        menuItemRepository.save(menuItem);

        return new ResponseEntity<>(
                "Menu item updated successfully",
                HttpStatusCode.valueOf(200)
        );

//        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(menuItemRequestDTO.getRestaurantId());
//
//        if(restaurantOptional.isEmpty()){
//            return new ResponseEntity<>("Restaurant id does not exist", HttpStatusCode.valueOf(400));
//        }
//
//        Restaurant restaurant = restaurantOptional.get();
//
//        MenuItem menuItem = convertMenuItemRequestDTOToMenuItem(menuItemRequestDTO,restaurant);
//        menuItemRepository.save(menuItem);
//
//        return new ResponseEntity<>("Menu item updated successfully", HttpStatusCode.valueOf(201));
      //  return null;
    }

    public ResponseEntity<String> deleteMenuItem(Long id) {

        if (id == null || id <= 0) {
            return new ResponseEntity<>("Menu item id must be greater than 0",
                    HttpStatusCode.valueOf(400));
        }

        Optional<MenuItem> menuItemOptional = menuItemRepository.findById(id);

        if (menuItemOptional.isEmpty()) {
            return new ResponseEntity<>("Menu item id does not exist",
                    HttpStatusCode.valueOf(404));
        }

        MenuItem menuItem = menuItemOptional.get();

       // menuItemRepository.delete(menuItem);

        menuItemRepository.deleteById(id);
        return new ResponseEntity<>(
                "Menu item and all its variants deleted successfully",
                HttpStatusCode.valueOf(200)
        );

    }


    public ResponseEntity<MenuItemResponseDTO> getMenuItem(Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }

        Optional<MenuItem> menuItemOptional =
                menuItemRepository.findById(id);

        if (menuItemOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }

        MenuItem menuItem = menuItemOptional.get();

        MenuItemResponseDTO menuItemResponseDTO =
                convertMenuItemToMenuItemResponseDTO(menuItem);

        return new ResponseEntity<>(menuItemResponseDTO, HttpStatusCode.valueOf(200));

    }

    public ResponseEntity<List<MenuItemResponseDTO>> getAllMenuItems() {

        List<MenuItem> menuItems = menuItemRepository.findAll();

        List<MenuItemResponseDTO> menuItemResponseDTOS = convertMenuItemListToMenuItemResponseDTO(menuItems);

        return new ResponseEntity<>(menuItemResponseDTOS,
                HttpStatusCode.valueOf(200)
        );


    }

    private List<MenuItemResponseDTO> convertMenuItemListToMenuItemResponseDTO(List<MenuItem> menuItems) {
        List<MenuItemResponseDTO> menuItemResponseDTOS =
                new ArrayList<>();

        for (MenuItem menuItem : menuItems) {

            MenuItemResponseDTO menuItemResponseDTO =
                    convertMenuItemToMenuItemResponseDTO(menuItem);

            menuItemResponseDTOS.add(menuItemResponseDTO);
        }

        return menuItemResponseDTOS;
    }

    private MenuItemResponseDTO convertMenuItemToMenuItemResponseDTO(
            MenuItem menuItem) {

        MenuItemResponseDTO menuItemResponseDTO =
                new MenuItemResponseDTO();

        menuItemResponseDTO.setId(menuItem.getId());

        menuItemResponseDTO.setName(menuItem.getName());

        menuItemResponseDTO.setDescription(
                menuItem.getDescription()
        );

        menuItemResponseDTO.setMenuItemType(
                menuItem.getMenuItemType()
        );

        menuItemResponseDTO.setLabel(
                menuItem.getLabel()
        );

        List<MenuItemVariantResponseDTO> menuItemVariantResponseDTOS =
                new ArrayList<>();

        for (MenuItemVariant menuItemVariant :
                menuItem.getMenuItemVariantList()) {

            MenuItemVariantResponseDTO menuItemVariantResponseDTO =
                    new MenuItemVariantResponseDTO();

            menuItemVariantResponseDTO.setDishVariantName(
                    menuItemVariant.getName()
            );

            menuItemVariantResponseDTO.setAvailable(
                    menuItemVariant.isAvailable()
            );

            menuItemVariantResponseDTO.setPrice(
                    menuItemVariant.getPrice()
            );

            menuItemVariantResponseDTOS.add(
                    menuItemVariantResponseDTO
            );
        }

        menuItemResponseDTO.setMenuItemVariants(
                menuItemVariantResponseDTOS
        );

        return menuItemResponseDTO;
    }

}
