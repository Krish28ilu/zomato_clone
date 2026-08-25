package com.study.zomato_clone.repository;

import com.study.zomato_clone.entity.MenuItem;
import com.study.zomato_clone.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
    MenuItem findByRestaurantAndName(Restaurant restaurant, String name);

}
