package com.rachidy.sassgestionstockapp.repositories;

import com.rachidy.sassgestionstockapp.entities.StockMvt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMvtRepository extends JpaRepository<StockMvt,String> {
}
