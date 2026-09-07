package com.study.zomato_clone.service;

import com.study.zomato_clone.dto.MenuItemResponseDTO;
import com.study.zomato_clone.dto.MenuItemVariantResponseDTO;
import com.study.zomato_clone.dto.RestaurantRequestDTO;
import com.study.zomato_clone.dto.RestaurantResponseDTO;
import com.study.zomato_clone.entity.Address;
import com.study.zomato_clone.entity.MenuItem;
import com.study.zomato_clone.entity.MenuItemVariant;
import com.study.zomato_clone.entity.Restaurant;
import com.study.zomato_clone.exception.InvalidRequestException;
import com.study.zomato_clone.exception.NoSuchRestaurantExistException;
import com.study.zomato_clone.exception.RestaurantAlreadyExistException;
import com.study.zomato_clone.repository.RestaurantRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RestaurantService {


    @Autowired
    private RestaurantRepository restaurantRepository;

    public RestaurantResponseDTO addRestaurant(RestaurantRequestDTO restaurantRequestDTO) {
        validateRestaurantRequestDTO(restaurantRequestDTO);
        // final after  all validations are passed then save restuarant
        Restaurant restaurant = convertRestaurantDTOtoEntity(restaurantRequestDTO);
        restaurant = restaurantRepository.save(restaurant);
        return convertRestaurantToRestaurantResponseDTO(restaurant);

    }


    private void validateRestaurantRequestDTO(RestaurantRequestDTO restaurantRequestDTO) {
        if(ObjectUtils.isEmpty(restaurantRequestDTO)){
            throw new InvalidRequestException("invalid request object");
        }
        if(ObjectUtils.isEmpty(restaurantRequestDTO.getName())){
            throw new InvalidRequestException("invalid restaurant name ");
        }
        if(ObjectUtils.isEmpty(restaurantRequestDTO.getCountry())){
            throw new InvalidRequestException("invalid restaurant county ");
        }

        if(ObjectUtils.isEmpty(restaurantRequestDTO.getPhoneNumber())){
            throw new InvalidRequestException("invalid restaurant phone number ");
        }
        if(ObjectUtils.isEmpty(restaurantRequestDTO.getStreetLine1())){
            throw new InvalidRequestException("invalid restaurant street line 1 ");
        }
        if(ObjectUtils.isEmpty(restaurantRequestDTO.getPinCode())){
            throw new InvalidRequestException("invalid restaurant pincode ");
        }
        if(ObjectUtils.isEmpty(restaurantRequestDTO.getLatitude())){
            throw new InvalidRequestException("invalid restaurant latitude ");
        }
        if(ObjectUtils.isEmpty(restaurantRequestDTO.getLongitude())){
            throw new InvalidRequestException("invalid restaurant longitude ");

        }

        if(!restaurantRequestDTO.getPhoneNumber().matches("\\d{10}")){
            throw new InvalidRequestException("invalid restaurant phone number. ");
        }

        if(!restaurantRequestDTO.getPinCode().matches("^[1-9][0-9]{5}$")){
            throw new InvalidRequestException("invalid restaurant pincode. ");
        }

        if(restaurantRequestDTO.getLatitude() < -90 ||
                restaurantRequestDTO.getLatitude() > 90){
            throw new InvalidRequestException("invalid restaurant latitude .");
        }

        if(restaurantRequestDTO.getLongitude() < -180 ||
                restaurantRequestDTO.getLongitude() > 180){
            throw new InvalidRequestException("invalid restaurant longitude .");
        }

        Restaurant existingRestaurant =
                restaurantRepository.findByPhone(restaurantRequestDTO.getPhoneNumber());

        if(!ObjectUtils.isEmpty(existingRestaurant)){
            throw new RestaurantAlreadyExistException("this restaurant already exist");
        }
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
        if(restaurant == null) {
            throw new NoSuchRestaurantExistException("restaurant not found");
        }
        return convertRestaurantToRestaurantResponseDTO(restaurant);

    }

    public RestaurantResponseDTO convertRestaurantToRestaurantResponseDTO(Restaurant restaurant) {
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

    private void validateUpdateRestaurant(RestaurantRequestDTO restaurantRequestDTO, Long id, Restaurant restaurant) {

        if (restaurant == null) {
            throw new NoSuchRestaurantExistException("restaurant not found");
        }

        if (ObjectUtils.isEmpty(restaurantRequestDTO)) {
            throw new InvalidRequestException("invalid request object");
        }

        if (ObjectUtils.isEmpty(restaurantRequestDTO.getName()) ||
                ObjectUtils.isEmpty(restaurantRequestDTO.getCountry()) ||
                ObjectUtils.isEmpty(restaurantRequestDTO.getPhoneNumber()) ||
                ObjectUtils.isEmpty(restaurantRequestDTO.getStreetLine1()) ||
                ObjectUtils.isEmpty(restaurantRequestDTO.getPinCode()) ||
                ObjectUtils.isEmpty(restaurantRequestDTO.getLatitude()) ||
                ObjectUtils.isEmpty(restaurantRequestDTO.getLongitude())) {

            throw new InvalidRequestException("invalid request object");
        }

        if (!restaurantRequestDTO.getPhoneNumber().matches("\\d{10}")) {
            throw new InvalidRequestException("invalid restaurant phone number. ");
        }

        if (!restaurantRequestDTO.getPinCode().matches("^[1-9][0-9]{5}$")) {
            throw new InvalidRequestException("invalid restaurant pincode. ");
        }

        if (restaurantRequestDTO.getLatitude() < -90 || restaurantRequestDTO.getLatitude() > 90) {

            throw new InvalidRequestException("invalid restaurant latitude .");
        }

        if (restaurantRequestDTO.getLongitude() < -180 ||
                restaurantRequestDTO.getLongitude() > 180) {

            throw  new InvalidRequestException("invalid restaurant longitude .");
        }

        Restaurant existingRestaurant = restaurantRepository.findByPhone(
                restaurantRequestDTO.getPhoneNumber()
        );

        if (existingRestaurant != null && !existingRestaurant.getId().equals(id)) {

            throw new   InvalidRequestException(" restaurant phone number already in use. ");
        }

    }
    public RestaurantResponseDTO updateRestaurant(Long id, RestaurantRequestDTO restaurantRequestDTO) {

       Restaurant restaurant = restaurantRepository.findById(id).orElse(null);

        validateUpdateRestaurant(restaurantRequestDTO, id, restaurant);

        Address address = restaurant.getAddress();

        address.setStreetLine1(restaurantRequestDTO.getStreetLine1());
        address.setStreetLine2(restaurantRequestDTO.getStreetLine2());
        address.setCountry(restaurantRequestDTO.getCountry());
        address.setPinCode(restaurantRequestDTO.getPinCode());
        address.setLatitude(restaurantRequestDTO.getLatitude());
        address.setLongitude(restaurantRequestDTO.getLongitude());

        restaurant.setAddress(address);

        restaurantRepository.save(restaurant);

        return convertRestaurantToRestaurantResponseDTO(restaurant);
    }

    public RestaurantResponseDTO deleteRestaurant(Long id) {


        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        if (restaurant == null) {
            throw new NoSuchRestaurantExistException("restaurant not found");
        }

        restaurantRepository.deleteById(id);
        return convertRestaurantToRestaurantResponseDTO(restaurant);
    }
}
