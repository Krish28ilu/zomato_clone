package com.study.zomato_clone.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
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
