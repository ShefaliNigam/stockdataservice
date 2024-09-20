package com.stockcharting.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockcharting.app.entity.StockEntity;




@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {
}

