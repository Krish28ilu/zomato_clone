package com.study.zomato_clone.service;

import com.study.zomato_clone.dto.MenuItemResponseDTO;
import com.study.zomato_clone.dto.MenuItemVariantResponseDTO;
import com.study.zomato_clone.dto.RestaurantRequestDTO;
import com.study.zomato_clone.dto.RestaurantResponseDTO;
import com.study.zomato_clone.entity.Address;
import com.study.zomato_clone.entity.MenuItem;
import com.study.zomato_clone.entity.MenuItemVariant;
import com.study.zomato_clone.entity.Restaurant;
import com.study.zomato_clone.repository.RestaurantRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RestaurantService {


    @Autowired
    private RestaurantRepository restaurantRepository;

    public void addRestaurant(RestaurantRequestDTO restaurantRequestDTO) {


        if(ObjectUtils.isEmpty(restaurantRequestDTO)){
            return;
        }
        if(ObjectUtils.isEmpty(restaurantRequestDTO.getName())){
            return;
        }
        if(ObjectUtils.isEmpty(restaurantRequestDTO.getCountry())){
            return;
        }

        if(ObjectUtils.isEmpty(restaurantRequestDTO.getPhoneNumber())){
            return;
        }
        if(ObjectUtils.isEmpty(restaurantRequestDTO.getStreetLine1())){
            return;
        }
        if(ObjectUtils.isEmpty(restaurantRequestDTO.getPinCode())){
            return;
        }
        if(ObjectUtils.isEmpty(restaurantRequestDTO.getLatitude())){
            return;
        }
        if(ObjectUtils.isEmpty(restaurantRequestDTO.getLongitude())){
            return;
        }

        if(!restaurantRequestDTO.getPhoneNumber().matches("\\d{10}")){
            return;
        }

        if(!restaurantRequestDTO.getPinCode().matches("^[1-9][0-9]{5}$")){
            return;
        }

        if(restaurantRequestDTO.getLatitude() < -90 ||
                restaurantRequestDTO.getLatitude() > 90){
            return;
        }

        if(restaurantRequestDTO.getLongitude() < -180 ||
                restaurantRequestDTO.getLongitude() > 180){
            return;
        }

        Restaurant existingRestaurant =
                restaurantRepository.findByPhone(restaurantRequestDTO.getPhoneNumber());

        if(!ObjectUtils.isEmpty(existingRestaurant)){
            return;
        }

        // final after  all validations are passed then save restuarant
        Restaurant restaurant = convertRestaurantDTOtoEntity(restaurantRequestDTO);
        restaurantRepository.save(restaurant);

    }

    private Restaurant convertRestaurantDTOtoEntity(RestaurantRequestDTO restaurantRequestDTO) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequestDTO.getName());
        restaurant.setPhone(restaurantRequestDTO.getPhoneNumber());

        Address address = new Address();

        address.setCountry(restaurantRequestDTO.getCountry());
        address.setLatitude(restaurantRequestDTO.getLatitude());
        address.setLongitude(restaurantRequestDTO.getLongitude());
        restaurant.setAddress(address);

        return restaurant;
    }

    public RestaurantResponseDTO getRestaurant(Long id) {
        Restaurant restaurant =  restaurantRepository.findById(id).orElse(null);
//        if(restaurant == null)
//            return null;

        return convertRestaurantToRestaurantResponseDTO(restaurant);

    }

    private RestaurantResponseDTO convertRestaurantToRestaurantResponseDTO(Restaurant restaurant) {
        RestaurantResponseDTO restaurantResponseDTO = new RestaurantResponseDTO();

        restaurantResponseDTO.setName(restaurant.getName());
        restaurantResponseDTO.setId(restaurant.getId());
        restaurantResponseDTO.setPhoneNumber(restaurant.getPhone());
        restaurantResponseDTO.setCountry(restaurant.getAddress().getCountry());
        restaurantResponseDTO.setStreetLine1(restaurant.getAddress().getStreetLine1());


        List<MenuItemResponseDTO> menuItemResponseDTOList = new ArrayList<>();
        List<MenuItem> menuItemList  = restaurant.getMenuItemList();

        for(MenuItem menuItem: menuItemList){
            MenuItemResponseDTO menuItemResponseDTO = new MenuItemResponseDTO();
            menuItemResponseDTO.setName(menuItem.getName());
            menuItemResponseDTO.setDescription(menuItem.getDescription());
            menuItemResponseDTO.setLabel(menuItem.getLabel());
            menuItemResponseDTO.setMenuItemType(menuItem.getMenuItemType());

            List<MenuItemVariantResponseDTO> menuItemVariantResponseDTOS = new ArrayList<>();
            List<MenuItemVariant> menuItemVariantList = menuItem.getMenuItemVariantList();

            for(MenuItemVariant menuItemVariant: menuItemVariantList){
                MenuItemVariantResponseDTO menuItemVariantResponseDTO = new MenuItemVariantResponseDTO();

                menuItemVariantResponseDTO.setAvailable(menuItemVariant.isAvailable());
                menuItemVariantResponseDTO.setDishVariantName(menuItemVariant.getName());
                menuItemVariantResponseDTO.setPrice(menuItemVariant.getPrice());
                menuItemVariantResponseDTOS.add(menuItemVariantResponseDTO);

            }
            menuItemResponseDTO.setMenuItemVariants(menuItemVariantResponseDTOS);


            menuItemResponseDTOList.add(menuItemResponseDTO);
        }

        restaurantResponseDTO.setMenuItems(menuItemResponseDTOList);


        return restaurantResponseDTO;


    }

    public String updateRestaurant(
            Long id,
            RestaurantRequestDTO restaurantRequestDTO) {

        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);

        if (restaurant == null) {
            return "Restaurant not found";
        }

        if (ObjectUtils.isEmpty(restaurantRequestDTO)) {
            return "Invalid data";
        }

        if (ObjectUtils.isEmpty(restaurantRequestDTO.getName()) ||
                ObjectUtils.isEmpty(restaurantRequestDTO.getCountry()) ||
                ObjectUtils.isEmpty(restaurantRequestDTO.getPhoneNumber()) ||
                ObjectUtils.isEmpty(restaurantRequestDTO.getStreetLine1()) ||
                ObjectUtils.isEmpty(restaurantRequestDTO.getPinCode()) ||
                ObjectUtils.isEmpty(restaurantRequestDTO.getLatitude()) ||
                ObjectUtils.isEmpty(restaurantRequestDTO.getLongitude())) {

            return "Invalid data";
        }

        if (!restaurantRequestDTO.getPhoneNumber().matches("\\d{10}")) {
            return "Invalid data";
        }

        if (!restaurantRequestDTO.getPinCode().matches("^[1-9][0-9]{5}$")) {
            return "Invalid data";
        }

        if (restaurantRequestDTO.getLatitude() < -90 || restaurantRequestDTO.getLatitude() > 90) {

            return "Invalid data";
        }

        if (restaurantRequestDTO.getLongitude() < -180 ||
                restaurantRequestDTO.getLongitude() > 180) {

            return "Invalid data";
        }

        Restaurant existingRestaurant = restaurantRepository.findByPhone(
                        restaurantRequestDTO.getPhoneNumber()
                );

        if (existingRestaurant != null &&
                !existingRestaurant.getId().equals(id)) {

            return "Phone number already exists";
        }


        restaurant.setName(restaurantRequestDTO.getName());
        restaurant.setPhone(restaurantRequestDTO.getPhoneNumber());


        Address address = restaurant.getAddress();

        address.setStreetLine1(restaurantRequestDTO.getStreetLine1());
        address.setStreetLine2(restaurantRequestDTO.getStreetLine2());
        address.setCountry(restaurantRequestDTO.getCountry());
        address.setPinCode(restaurantRequestDTO.getPinCode());
        address.setLatitude(restaurantRequestDTO.getLatitude());
        address.setLongitude(restaurantRequestDTO.getLongitude());

        restaurant.setAddress(address);

        restaurantRepository.save(restaurant);

        return "Restaurant updated successfully";
    }

    public String deleteRestaurant(Long id) {

        if (!restaurantRepository.existsById(id)) {
            return "Restaurant not found";
        }

        restaurantRepository.deleteById(id);

        return "Restaurant deleted successfully";
    }
}
