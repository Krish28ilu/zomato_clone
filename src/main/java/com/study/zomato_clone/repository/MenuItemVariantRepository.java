package com.study.zomato_clone.repository;

import com.study.zomato_clone.entity.MenuItemVariant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemVariantRepository extends JpaRepository<MenuItemVariant,Long> {

}
