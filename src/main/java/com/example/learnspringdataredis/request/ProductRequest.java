package com.example.learnspringdataredis.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    private Integer id;

    private String productName;

    private String quantity;

    private Integer status;
}
