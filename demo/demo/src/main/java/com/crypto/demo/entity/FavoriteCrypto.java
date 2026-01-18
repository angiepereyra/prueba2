package com.crypto.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Builder
@Data
@Entity
@Table(name = "favorites_crypto")
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteCrypto {
    @Id
    private String id;
    private String name;
    private String symbol;
    private Double targetPrice;
    private LocalDateTime dateAdded;


}
