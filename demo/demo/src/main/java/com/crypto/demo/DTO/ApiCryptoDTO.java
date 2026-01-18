package com.crypto.demo.DTO;

import com.crypto.demo.entity.FavoriteCrypto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiCryptoDTO {
    private String id;
    private String symbol;
    private String name;
    private String image;
    private Double current_price;
    private Double market_cap;
    private Integer market_cap_rank;
    private Double fully_diluted_valuation;
    private Double total_volume;
    private Double high_24h;
    private Double low_24h;
    private Double price_change_24h;
    private Double price_change_percentage_24h;
    private Double market_cap_change_24h;
    private Double market_cap_change_percentage_24h;
    private Double circulating_supply;
    private Double total_supply;
    private Double max_supply;
    private Double ath;
    private Double ath_change_percentage;
    private LocalDateTime ath_date;
    private Double atl;
    private Double atl_change_percentage;
    private LocalDateTime atl_date;

    @Data public static class Roi {private LocalDateTime times; private String currency; private Double percentage;}
    private LocalDateTime last_updated;
    private String alertMessage;

    @Data
    public static class RestResponse {
        private List<ApiCryptoDTO> results;
    }

    public ApiCryptoDTO(String id, String name, Double current_price, String alertMessage) {
        this.id = id;
        this.name = name;
        this.current_price = current_price;
        this.alertMessage = alertMessage;
    }

}