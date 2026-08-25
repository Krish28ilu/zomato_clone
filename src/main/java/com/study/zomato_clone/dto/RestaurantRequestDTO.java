package com.study.zomato_clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequestDTO {


    private String name;
    private String phoneNumber;
    private String streetLine1;
    private String streetLine2;
    private String country;
    private String pinCode;

    private Double latitude;
    private Double longitude;
}
