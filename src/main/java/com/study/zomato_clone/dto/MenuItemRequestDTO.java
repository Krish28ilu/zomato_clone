package com.study.zomato_clone.dto;

import com.study.zomato_clone.enums.MenuItemType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class MenuItemRequestDTO {

    private String name;

    private String description;

    private MenuItemType menuItemType;

    private Long restaurantId;

    private String label;

    private List<MenuItemVariantRequestDTO> menuItemVariants;
}
