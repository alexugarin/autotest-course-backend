package com.tests.lab2.repositories;

import com.tests.lab2.entities.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Long> {
    @Query("select c from Calculation c where c.operation = :operation and c.systemA = :numberSystem and " +
            "c.calcDate >= :startDate and c.calcDate <= :endDate")
    List<Calculation> findByParams(@Param("operation") String operation,
                                   @Param("numberSystem") String numberSystem,
                                   @Param("startDate") Timestamp startDate,
                                   @Param("endDate") Timestamp endDate);
}
