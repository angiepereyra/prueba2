package com.crypto.demo.controller;

import com.crypto.demo.DTO.ApiCryptoDTO;
import com.crypto.demo.entity.FavoriteCrypto;
import com.crypto.demo.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crypto")
@CrossOrigin(origins="*")
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @GetMapping("/getAll")
    public ResponseEntity<List<FavoriteCrypto>> getAllCrypto() {
        return ResponseEntity.ok(cryptoService.getTenCryptos());
    }

    @PostMapping("/postNewFavorite")
    public ResponseEntity<FavoriteCrypto> saveFavorite(@RequestBody FavoriteCrypto favoriteCrypto) {
        cryptoService.saveFavorite(favoriteCrypto);
        return new ResponseEntity<>(favoriteCrypto, org.springframework.http.HttpStatus.CREATED);
    }

    @GetMapping("/getAlets")
    public List<ApiCryptoDTO> getAlerts() {
        return(cryptoService.getMarketWithAlerts());
    }

}
