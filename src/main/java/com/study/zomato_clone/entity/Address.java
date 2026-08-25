package com.study.zomato_clone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;
@Data
@AllArgsConstructor
@NoArgsConstructor
@SoftDelete
@Entity(name = "Address")

public class Address extends BaseEntity {

    private String streetLine1;
    private String streetLine2;
    private String country;
    private String pinCode;
    private Double latitude;
    private Double longitude;
    private String label;
    private boolean defaultAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
