package com.study.zomato_clone.dto;

import com.study.zomato_clone.entity.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

public class UserRequestDTO {


    private String name;
    private String email;
    private String phoneNumber;
    private String streetLine1;
    private String streetLine2;
    private String country;
    private String pinCode;
    private Double latitude;
    private Double longitude;
    private String label;
    private boolean defaultAddress;
}
