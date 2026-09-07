package com.study.zomato_clone.service;

import com.study.zomato_clone.dto.RestaurantResponseDTO;
import com.study.zomato_clone.dto.UserRequestDTO;
import com.study.zomato_clone.dto.UserResponseDTO;
import com.study.zomato_clone.entity.Address;
import com.study.zomato_clone.entity.Restaurant;
import com.study.zomato_clone.entity.User;
import com.study.zomato_clone.exception.InvalidRequestException;
import com.study.zomato_clone.exception.NoSuchUserExistException;
import com.study.zomato_clone.repository.RestaurantRepository;
import com.study.zomato_clone.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;

    public List<UserResponseDTO> findAll() {
        List<User> userList = userRepository.findAll();

        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();

        for(User user : userList){

            UserResponseDTO userResponseDTO = new UserResponseDTO();

            userResponseDTO.setName(user.getName());
            userResponseDTO.setEmail(user.getEmail());
            userResponseDTO.setPhoneNumber(user.getPhoneNumber());

            List<Address> addressList = user.getAddressList();

            for(Address address : addressList){

                userResponseDTO.setStreetLine1(address.getStreetLine1());
                userResponseDTO.setStreetLine2(address.getStreetLine2());
                userResponseDTO.setCountry(address.getCountry());
                userResponseDTO.setPinCode(address.getPinCode());
                userResponseDTO.setLatitude(address.getLatitude());
                userResponseDTO.setLongitude(address.getLongitude());
                userResponseDTO.setLabel(address.getLabel());
                userResponseDTO.setDefaultAddress(address.isDefaultAddress());

            }

            userResponseDTOList.add(userResponseDTO);
        }

        return userResponseDTOList;
    }

    public ResponseEntity<List<UserResponseDTO>> findByName(String name) {
        List<User> userList = userRepository.findByName(name);

        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();

        for(User user : userList){

            UserResponseDTO userResponseDTO = new UserResponseDTO();

            userResponseDTO.setName(user.getName());
            userResponseDTO.setEmail(user.getEmail());
            userResponseDTO.setPhoneNumber(user.getPhoneNumber());

            List<Address> addressList = user.getAddressList();

            for(Address address : addressList){

                userResponseDTO.setStreetLine1(address.getStreetLine1());
                userResponseDTO.setStreetLine2(address.getStreetLine2());
                userResponseDTO.setCountry(address.getCountry());
                userResponseDTO.setPinCode(address.getPinCode());
                userResponseDTO.setLatitude(address.getLatitude());
                userResponseDTO.setLongitude(address.getLongitude());
                userResponseDTO.setLabel(address.getLabel());
                userResponseDTO.setDefaultAddress(address.isDefaultAddress());
            }

            userResponseDTOList.add(userResponseDTO);
        }

        return new ResponseEntity<>(
                userResponseDTOList,
                HttpStatusCode.valueOf(200)
        );
    }

    public UserResponseDTO findById(Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new InvalidRequestException("User id does not exist");
        }

        return convertUserToUserResponseDTO(user);
    }


    private UserResponseDTO convertUserToUserResponseDTO(User user) {

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhoneNumber(user.getPhoneNumber());

        List<Address> addressList = user.getAddressList();

        for (Address address : addressList) {

            userResponseDTO.setStreetLine1(address.getStreetLine1());
            userResponseDTO.setStreetLine2(address.getStreetLine2());
            userResponseDTO.setCountry(address.getCountry());
            userResponseDTO.setPinCode(address.getPinCode());
            userResponseDTO.setLatitude(address.getLatitude());
            userResponseDTO.setLongitude(address.getLongitude());
            userResponseDTO.setLabel(address.getLabel());
            userResponseDTO.setDefaultAddress(address.isDefaultAddress());

        }

        return userResponseDTO;
    }
    public  UserResponseDTO addUser(UserRequestDTO userRequestDTO) {

        validateUser(userRequestDTO);

        User user = new User();

        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhoneNumber(userRequestDTO.getPhoneNumber());

        Address address = new Address();

        address.setStreetLine1(userRequestDTO.getStreetLine1());
        address.setStreetLine2(userRequestDTO.getStreetLine2());
        address.setCountry(userRequestDTO.getCountry());
        address.setPinCode(userRequestDTO.getPinCode());
        address.setLatitude(userRequestDTO.getLatitude());
        address.setLongitude(userRequestDTO.getLongitude());
        address.setLabel(userRequestDTO.getLabel());
        address.setDefaultAddress(userRequestDTO.isDefaultAddress());


        userRepository.save(user);

        return convertUserToUserResponseDTO(user);

    }


    private void validateUser(UserRequestDTO userRequestDTO) {

        if (    Objects.isNull(userRequestDTO)) {
         throw new InvalidRequestException("Object is null");
        }

        if (StringUtils.isBlank(userRequestDTO.getName())) {
           throw new InvalidRequestException("Invalid name ");
        }

        if (StringUtils.isBlank(userRequestDTO.getEmail())) {
            throw new InvalidRequestException("Invalid email ");
        }

        if (StringUtils.isBlank(userRequestDTO.getPhoneNumber())) {
            throw new InvalidRequestException("Invalid phone number ");
        }

        if (!userRequestDTO.getPhoneNumber().matches("\\d{10}")) {
           throw new InvalidRequestException("Invalid phone number ");
        }

        if (StringUtils.isBlank(userRequestDTO.getStreetLine1())) {
           throw new InvalidRequestException("Invalid street line 1 ");
        }

        if (StringUtils.isBlank(userRequestDTO.getCountry())) {
           throw new InvalidRequestException("Invalid country ");

        }

        if (StringUtils.isBlank(userRequestDTO.getPinCode())) {
           throw new InvalidRequestException("Invalid pincode ");
        }

        if (!userRequestDTO.getPinCode().matches("^[1-9][0-9]{5}$")) {
            throw
                    new InvalidRequestException("Invalid pincode ");
        }

        if (userRequestDTO.getLatitude() == null) {
            throw new InvalidRequestException("Invalid latitude ");
        }

        if (userRequestDTO.getLongitude() == null) {
            throw new InvalidRequestException("Invalid longitude ");
        }

        if (userRequestDTO.getLatitude() < -90 ||
                userRequestDTO.getLatitude() > 90) {

            throw new InvalidRequestException("Invalid latitude ");
        }

        if (userRequestDTO.getLongitude() < -180 ||
                userRequestDTO.getLongitude() > 180) {

           throw new InvalidRequestException("Invalid longitude ");
        }

    }

    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO, Long id) {
        validateUser(userRequestDTO);

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
           throw new NoSuchUserExistException("User with id " + id + " does not exist");
        }

        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhoneNumber(userRequestDTO.getPhoneNumber());

        List<Address> addressList = user.getAddressList();

        if (!addressList.isEmpty()) {

            Address address = addressList.get(0);

            address.setStreetLine1(userRequestDTO.getStreetLine1());
            address.setStreetLine2(userRequestDTO.getStreetLine2());
            address.setCountry(userRequestDTO.getCountry());
            address.setPinCode(userRequestDTO.getPinCode());
            address.setLatitude(userRequestDTO.getLatitude());
            address.setLongitude(userRequestDTO.getLongitude());
            address.setLabel(userRequestDTO.getLabel());
            address.setDefaultAddress(userRequestDTO.isDefaultAddress());

        }

        userRepository.save(user);

       return convertUserToUserResponseDTO(user);

    }

    public UserResponseDTO deleteUser(Long id) {
        if (id == null || id <= 0) {
          throw new InvalidRequestException("Invalid id ");
        }

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new NoSuchUserExistException("User with id " + id + " does not exist");
        }

        userRepository.deleteById(id);

        return convertUserToUserResponseDTO(user);
    }

    public List<RestaurantResponseDTO> getNearbyRestaurants(Double lat, Double lon) {

        List<Restaurant> restaurants = restaurantRepository.findNearbyRestaurants(lon,lat);
        List<RestaurantResponseDTO> restaurantResponseDTOList = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
                RestaurantResponseDTO restaurantResponseDTO = restaurantService.convertRestaurantToRestaurantResponseDTO(restaurant);
                restaurantResponseDTOList.add(restaurantResponseDTO);
            }
        return  restaurantResponseDTOList;
    }
}
