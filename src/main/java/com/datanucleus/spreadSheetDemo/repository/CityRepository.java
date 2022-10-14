package com.datanucleus.spreadSheetDemo.repository;

import com.datanucleus.spreadSheetDemo.model.CityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<CityModel, Long> {
}
