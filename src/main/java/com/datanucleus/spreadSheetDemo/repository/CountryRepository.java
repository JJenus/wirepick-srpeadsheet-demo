package com.datanucleus.spreadSheetDemo.repository;

import com.datanucleus.spreadSheetDemo.model.CountryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryModel, Long> {

}
