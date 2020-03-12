package com.thinkss.paycheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thinkss.paycheck.entity.GeoLocation;


public interface GeoLocationRepository extends JpaRepository<GeoLocation, Long> {

}
