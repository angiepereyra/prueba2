package com.crypto.demo.service;

import com.crypto.demo.DTO.ApiCryptoDTO;
import com.crypto.demo.entity.FavoriteCrypto;
import com.crypto.demo.repository.FavoriteCryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CryptoService {
    @Autowired
    private RestTemplate restTemplate;

    String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=10&page=1&sparkline=false";

    public List<FavoriteCrypto> getTenCryptos(){
        ApiCryptoDTO[] response = restTemplate.getForObject(url, ApiCryptoDTO[].class);
        if (response == null) return new ArrayList<>();
        List<ApiCryptoDTO> marketList = Arrays.asList(response);
        return Arrays.stream(response).map(ext ->
                FavoriteCrypto.builder()
                        .id(ext.getId())
                        .name(ext.getName())
                        .symbol(ext.getSymbol())
                        .targetPrice(ext.getCurrent_price())
                        .dateAdded(LocalDateTime.now())
                        .build()
        ).collect(Collectors.toList());
    }
    @Autowired
    private FavoriteCryptoRepository favoriteCryptoRepository;
    public void saveFavorite(FavoriteCrypto favoriteCrypto) {
        favoriteCryptoRepository.save(favoriteCrypto);
    }

    public List<ApiCryptoDTO> getMarketWithAlerts() {
        try {
            ApiCryptoDTO[] response = restTemplate.getForObject(url, ApiCryptoDTO[].class);
        if (response == null) return new ArrayList<>();
        List<FavoriteCrypto> favorites = favoriteCryptoRepository.findAll();
        return Arrays.stream(response).map(coin -> {
            Optional<FavoriteCrypto> fav = favorites.stream()
                    .filter(f -> f.getId().equals(coin.getId()))
                    .findFirst();

            String alertMessage = "Sin alerta";
            if (fav.isPresent()) {
                if (coin.getCurrent_price() <= fav.get().getTargetPrice()) {
                    alertMessage = "¡OFERTA! Bajó de tu precio objetivo";
                } else {
                    alertMessage = "Esperando que baje...";
                }
            }

            return new ApiCryptoDTO(
                    coin.getId(),
                    coin.getName(),
                    coin.getCurrent_price(),
                    alertMessage
            );
        }).collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("COINGECKO BLOQUEADO: " + e.getMessage());
            return new ArrayList<>(); // Devuelve lista vacía en lugar de lanzar ERROR 500
        }
    }
}
