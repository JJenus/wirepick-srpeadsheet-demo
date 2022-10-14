package com.datanucleus.spreadSheetDemo.repository;

import com.datanucleus.spreadSheetDemo.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {

}
