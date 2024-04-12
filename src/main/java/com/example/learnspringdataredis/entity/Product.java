package com.example.learnspringdataredis.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String productName;

    private String quantity;

    private Integer status;
}
