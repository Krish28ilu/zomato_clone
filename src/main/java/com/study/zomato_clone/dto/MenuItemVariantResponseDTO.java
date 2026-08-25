package com.study.zomato_clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemVariantResponseDTO {
    private String dishVariantName;
    private boolean available;
    private Double price;
}
