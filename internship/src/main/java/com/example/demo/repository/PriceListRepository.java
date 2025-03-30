package com.example.demo.repository;

import com.example.demo.model.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT p FROM PriceList p WHERE p.itemName = :itemName")
    List<PriceList> findByItemName(String itemName);

    @Transactional(readOnly = true)
    @Query("SELECT p FROM PriceList p WHERE p.status = :status")
    List<PriceList> findByStatus(String status);

    @Transactional(readOnly = true)
    @Query("SELECT p FROM PriceList p WHERE p.itemId = :itemId")
    PriceList findByItemId(Long itemId);

    @Transactional(readOnly = true)
    @Query("DELETE FROM PriceList p WHERE p.status = :status")
    void deleteByStatus(String status);

    @Transactional(readOnly = true)
    @Query("SELECT p FROM PriceList p WHERE trunc(p.startDate) = to_date(:startDate,'dd/MM/yyyy')")
    List<PriceList> findByStartDate(String startDate);
}
