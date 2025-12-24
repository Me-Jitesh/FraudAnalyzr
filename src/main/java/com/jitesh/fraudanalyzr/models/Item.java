package com.jitesh.fraudanalyzr.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    private String itemId;
    private String name;
    private Double price;
    private Integer qty;
}
