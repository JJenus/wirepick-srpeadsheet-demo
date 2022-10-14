package com.datanucleus.spreadSheetDemo.repository;

import com.datanucleus.spreadSheetDemo.model.StateModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<StateModel, Long> {
}
