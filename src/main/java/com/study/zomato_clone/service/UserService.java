package com.study.zomato_clone.service;

import com.study.zomato_clone.dto.UserRequestDTO;
import com.study.zomato_clone.dto.UserResponseDTO;
import com.study.zomato_clone.entity.Address;
import com.study.zomato_clone.entity.User;
import com.study.zomato_clone.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<List<UserResponseDTO>> findAll() {
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

        return new ResponseEntity<>( userResponseDTOList, HttpStatusCode.valueOf(200));
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

    public ResponseEntity<String> addUser(UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) {
            return new ResponseEntity<>(
                    "User request cannot be null",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (StringUtils.isBlank(userRequestDTO.getName())) {
            return new ResponseEntity<>(
                    "User name is required",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (StringUtils.isBlank(userRequestDTO.getEmail())) {
            return new ResponseEntity<>(
                    "Email is required",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (StringUtils.isBlank(userRequestDTO.getPhoneNumber())) {
            return new ResponseEntity<>(
                    "Phone number is required",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (!userRequestDTO.getPhoneNumber().matches("\\d{10}")) {
            return new ResponseEntity<>(
                    "Phone number must contain exactly 10 digits",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (StringUtils.isBlank(userRequestDTO.getStreetLine1())) {
            return new ResponseEntity<>(
                    "Street line 1 is required",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (StringUtils.isBlank(userRequestDTO.getCountry())) {
            return new ResponseEntity<>(
                    "Country is required",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (StringUtils.isBlank(userRequestDTO.getPinCode())) {
            return new ResponseEntity<>(
                    "Pin code is required",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (!userRequestDTO.getPinCode().matches("^[1-9][0-9]{5}$")) {
            return new ResponseEntity<>(
                    "Invalid pin code",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (userRequestDTO.getLatitude() == null) {
            return new ResponseEntity<>(
                    "Latitude is required",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (userRequestDTO.getLongitude() == null) {
            return new ResponseEntity<>(
                    "Longitude is required",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (userRequestDTO.getLatitude() < -90 ||
                userRequestDTO.getLatitude() > 90) {

            return new ResponseEntity<>(
                    "Invalid latitude",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (userRequestDTO.getLongitude() < -180 ||
                userRequestDTO.getLongitude() > 180) {

            return new ResponseEntity<>(
                    "Invalid longitude",
                    HttpStatusCode.valueOf(400)
            );
        }

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

        return new ResponseEntity<>(
                "User added successfully",
                HttpStatusCode.valueOf(201)
        );

    }

    public ResponseEntity<String> updateUser(UserRequestDTO userRequestDTO, Long id) {
        if (userRequestDTO == null) {
            return new ResponseEntity<>(
                    "User request cannot be null",
                    HttpStatusCode.valueOf(400)
            );
        }

        if (id == null || id <= 0) {
            return new ResponseEntity<>(
                    "User id must be greater than 0",
                    HttpStatusCode.valueOf(400)
            );
        }

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return new ResponseEntity<>(
                    "User id does not exist",
                    HttpStatusCode.valueOf(404)
            );
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

        return new ResponseEntity<>(
                "User updated successfully",
                HttpStatusCode.valueOf(200)
        );

    }

    public ResponseEntity<String> deleteUser(Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(
                    "User id must be greater than 0",
                    HttpStatusCode.valueOf(400)
            );
        }

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return new ResponseEntity<>(
                    "User id does not exist",
                    HttpStatusCode.valueOf(404)
            );
        }

        userRepository.deleteById(id);

        return new ResponseEntity<>(
                "User deleted successfully",
                HttpStatusCode.valueOf(200)
        );
    }
}
