package com.study.zomato_clone.repository;

import com.study.zomato_clone.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    Restaurant findByPhone(String phoneNumber);
}
