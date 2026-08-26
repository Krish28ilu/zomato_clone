package com.study.zomato_clone.service;

import com.study.zomato_clone.repository.MenuItemVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuItemVariantService {

    @Autowired
    private MenuItemVariantRepository menuItemVariantRepository;


}
