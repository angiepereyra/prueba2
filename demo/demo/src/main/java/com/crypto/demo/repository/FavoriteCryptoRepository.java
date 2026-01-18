package com.crypto.demo.repository;

import com.crypto.demo.entity.FavoriteCrypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteCryptoRepository extends JpaRepository<FavoriteCrypto, String> {
}
