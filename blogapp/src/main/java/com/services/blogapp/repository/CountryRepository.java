package com.services.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.services.blogapp.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
